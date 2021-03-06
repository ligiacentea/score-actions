package io.cloudslang.content.jclouds.services.impl;

import com.google.common.collect.Multimap;
import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.entities.inputs.CommonInputs;
import io.cloudslang.content.jclouds.entities.inputs.ImageInputs;
import io.cloudslang.content.jclouds.services.ImageService;
import io.cloudslang.content.jclouds.services.JCloudsComputeService;
import io.cloudslang.content.jclouds.services.helpers.AmazonImageServiceHelper;
import io.cloudslang.content.jclouds.services.helpers.Utils;
import io.cloudslang.content.jclouds.utils.InputsUtil;
import org.jclouds.ContextBuilder;
import org.jclouds.ec2.EC2Api;
import org.jclouds.ec2.domain.Image;
import org.jclouds.ec2.domain.Permission;
import org.jclouds.ec2.features.AMIApi;
import org.jclouds.ec2.options.CreateImageOptions;
import org.jclouds.ec2.options.DescribeImagesOptions;

import java.util.Set;

/**
 * Created by Mihai Tusa.
 * 5/4/2016.
 */
public class AmazonImageServiceImpl extends JCloudsComputeService implements ImageService {
    private static final String IMAGE_SUCCESSFULLY_DEREGISTER = "The image was successfully deregister.";
    private static final String LAUNCH_PERMISSIONS_SUCCESSFULLY_ADDED = "Launch permissions were successfully added.";
    private static final String LAUNCH_PERMISSIONS_SUCCESSFULLY_REMOVED = "Launch permissions were successfully removed.";
    private static final String LAUNCH_PERMISSIONS_SUCCESSFULLY_RESET = "Launch permissions were successfully reset.";

    EC2Api ec2Api;
    private String region;

    public AmazonImageServiceImpl(String endpoint, String identity, String credential, String proxyHost, String proxyPort) {
        super(endpoint, identity, credential, proxyHost, proxyPort);
    }

    @Override
    public String createImageInRegion(String region, String name, String serverId, String imageDescription, boolean imageNoReboot) {
        CreateImageOptions options = new CreateImageOptions().withDescription(imageDescription);
        if (imageNoReboot) {
            options.noReboot();
        }

        if (Constants.Miscellaneous.NOT_RELEVANT.equalsIgnoreCase(name)) {
            throw new RuntimeException(Constants.ErrorMessages.IMAGE_NAME_INPUT_REQUIRED);
        }

        return getAMIApi(region, true).createImageInRegion(region, name, serverId, options);
    }

    @Override
    public String deregisterImageInRegion(String region, String imageId) {
        getAMIApi(region, true).deregisterImageInRegion(region, imageId);

        return IMAGE_SUCCESSFULLY_DEREGISTER;
    }

    @Override
    public Set<? extends Image> describeImagesInRegion(CommonInputs commonInputs, ImageInputs imageInputs) {
        AmazonImageServiceHelper helper = new AmazonImageServiceHelper();
        DescribeImagesOptions options = helper.getDescribeImagesOptions(imageInputs, commonInputs.getDelimiter());
        Multimap<String, String> filtersMap = helper.getImageFiltersMap(imageInputs, commonInputs.getDelimiter());

        if (filtersMap.isEmpty()) {
            return getAMIApi(imageInputs.getCustomInputs().getRegion(), true)
                    .describeImagesInRegion(imageInputs.getCustomInputs().getRegion(), options);
        }

        return getAMIApi(imageInputs.getCustomInputs().getRegion(), true)
                .describeImagesInRegionWithFilter(imageInputs.getCustomInputs().getRegion(), filtersMap, options);
    }

    @Override
    public Permission getLaunchPermissionForImage(String region, String imageId) {
        return getAMIApi(region, true).getLaunchPermissionForImageInRegion(region, imageId);
    }

    @Override
    public String addLaunchPermissionsToImage(String region, Set<String> userIds, Set<String> userGroups, String imageId) {
        getAMIApi(region, true).addLaunchPermissionsToImageInRegion(region, userIds, userGroups, imageId);

        return LAUNCH_PERMISSIONS_SUCCESSFULLY_ADDED;
    }

    @Override
    public String removeLaunchPermissionsFromImage(String region, Set<String> userIds, Set<String> userGroups, String imageId) {
        getAMIApi(region, true).removeLaunchPermissionsFromImageInRegion(region, userIds, userGroups, imageId);

        return LAUNCH_PERMISSIONS_SUCCESSFULLY_REMOVED;
    }

    @Override
    public String resetLaunchPermissionsOnImage(String region, String imageId) {
        getAMIApi(region, true).resetLaunchPermissionsOnImageInRegion(region, imageId);

        return LAUNCH_PERMISSIONS_SUCCESSFULLY_RESET;
    }

    void lazyInit(String region) {
        this.region = InputsUtil.getAmazonRegion(region);
        init();
    }

    void init() {
        ContextBuilder contextBuilder = super.init(region, Constants.Apis.AMAZON_EC2_API);
        ec2Api = new Utils().getEC2Api(contextBuilder);
    }

    private AMIApi getAMIApi(String region, boolean isForRegion) {
        lazyInit(region);

        return isForRegion ? ec2Api.getAMIApiForRegion(region).get() : ec2Api.getAMIApi().get();
    }
}