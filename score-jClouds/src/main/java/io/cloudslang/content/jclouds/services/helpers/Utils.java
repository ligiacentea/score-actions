package io.cloudslang.content.jclouds.services.helpers;

import com.google.common.collect.Multimap;
import io.cloudslang.content.jclouds.entities.*;
import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.entities.inputs.ImageInputs;
import io.cloudslang.content.jclouds.entities.inputs.InstanceInputs;
import io.cloudslang.content.jclouds.utils.InputsUtil;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.ec2.EC2Api;

/**
 * Created by Mihai Tusa.
 * 6/15/2016.
 */
public class Utils {
    public EC2Api getEC2Api(ContextBuilder contextBuilder) {
        return contextBuilder.buildApi(EC2Api.class);
    }

    void updateInstanceFiltersMap(InstanceInputs instanceInputs, Multimap<String, String> filtersMap, String delimiter) {
        setInstanceRelevantFilters(instanceInputs, filtersMap);
        setTagFilters(instanceInputs, filtersMap, delimiter);

        updateFiltersMapEntry(filtersMap, CommonFilter.KERNEL_ID.getValue(), instanceInputs.getCustomInputs().getKernelId());
        updateFiltersMapEntry(filtersMap, CommonFilter.OWNER_ID.getValue(), instanceInputs.getCustomInputs().getOwnerId());
        updateFiltersMapEntry(filtersMap, CommonFilter.RAMDISK_ID.getValue(), instanceInputs.getCustomInputs().getRamdiskId());
        updateFiltersMapEntry(filtersMap, CommonFilter.ROOT_DEVICE_NAME.getValue(), instanceInputs.getCustomInputs().getRootDeviceName());
        updateFiltersMapEntry(filtersMap, CommonFilter.STATE_REASON_CODE.getValue(), instanceInputs.getCustomInputs().getStateReasonCode());
        updateFiltersMapEntry(filtersMap, CommonFilter.STATE_REASON_MESSAGE.getValue(), instanceInputs.getCustomInputs().getStateReasonMessage());

        updateFiltersMapEntry(filtersMap, InstanceFilter.AFFINITY.getValue(), instanceInputs.getAffinity());
        updateFiltersMapEntry(filtersMap, InstanceFilter.AVAILABILITY_ZONE.getValue(), instanceInputs.getCustomInputs().getAvailabilityZone());
        updateFiltersMapEntry(filtersMap, InstanceFilter.CLIENT_TOKEN.getValue(), instanceInputs.getClientToken());
        updateFiltersMapEntry(filtersMap, InstanceFilter.DNS_NAME.getValue(), instanceInputs.getDnsName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.GROUP_ID.getValue(), instanceInputs.getCustomInputs().getGroupId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.GROUP_NAME.getValue(), instanceInputs.getGroupName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.HOST_ID.getValue(), instanceInputs.getCustomInputs().getHostId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.IAM_INSTANCE_PROFILE_ARN.getValue(), instanceInputs.getIamArn());
        updateFiltersMapEntry(filtersMap, InstanceFilter.IMAGE_ID.getValue(), instanceInputs.getCustomInputs().getImageId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.INSTANCE_ID.getValue(), instanceInputs.getCustomInputs().getInstanceId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.INSTANCE_LIFECYCLE.getValue(), instanceInputs.getInstanceLifecycle());
        updateFiltersMapEntry(filtersMap, InstanceFilter.INSTANCE_GROUP_ID.getValue(), instanceInputs.getInstanceGroupId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.INSTANCE_GROUP_NAME.getValue(), instanceInputs.getInstanceGroupName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.IP_ADDRESS.getValue(), instanceInputs.getIpAddress());
        updateFiltersMapEntry(filtersMap, InstanceFilter.KEY_NAME.getValue(), instanceInputs.getKeyName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.LAUNCH_INDEX.getValue(), instanceInputs.getLaunchIndex());
        updateFiltersMapEntry(filtersMap, InstanceFilter.LAUNCH_TIME.getValue(), instanceInputs.getLaunchTime());
        updateFiltersMapEntry(filtersMap, InstanceFilter.PLACEMENT_GROUP_NAME.getValue(), instanceInputs.getPlacementGroupName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.PRIVATE_DNS_NAME.getValue(), instanceInputs.getPrivateDnsName());
        updateFiltersMapEntry(filtersMap, InstanceFilter.PRIVATE_IP_ADDRESS.getValue(), instanceInputs.getPrivateIpAddress());
        updateFiltersMapEntry(filtersMap, InstanceFilter.REASON.getValue(), instanceInputs.getReason());
        updateFiltersMapEntry(filtersMap, InstanceFilter.REQUESTER_ID.getValue(), instanceInputs.getRequesterId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.SUBNET_ID.getValue(), instanceInputs.getCustomInputs().getSubnetId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.VPC_ID.getValue(), instanceInputs.getCustomInputs().getVpcId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.RESERVATION_ID.getValue(), instanceInputs.getCustomInputs().getReservationId());
        updateFiltersMapEntry(filtersMap, InstanceFilter.SPOT_INSTANCE_REQUEST_ID.getValue(), instanceInputs.getSpotInstanceRequestId());

        updateFiltersMapEntry(filtersMap, AssociationFilter.PUBLIC_IP.getValue(), instanceInputs.getPublicIp());
        updateFiltersMapEntry(filtersMap, AssociationFilter.IP_OWNER_ID.getValue(), instanceInputs.getIpOwnerId());
        updateFiltersMapEntry(filtersMap, AssociationFilter.ALLOCATION_ID.getValue(), instanceInputs.getCustomInputs().getAllocationId());
        updateFiltersMapEntry(filtersMap, AssociationFilter.ASSOCIATION_ID.getValue(), instanceInputs.getCustomInputs().getAssociationId());

        updateFiltersMapEntry(filtersMap, BlockDeviceMappingFilter.ATTACH_TIME.getValue(), instanceInputs.getCustomInputs().getAttachTime());
        updateFiltersMapEntry(filtersMap, BlockDeviceMappingFilter.DEVICE_NAME.getValue(), instanceInputs.getCustomInputs().getBlockMappingDeviceName());
        updateFiltersMapEntry(filtersMap, BlockDeviceMappingFilter.VOLUME_ID.getValue(), instanceInputs.getCustomInputs().getVolumeId());

        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.DESCRIPTION.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceDescription());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.SUBNET_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceSubnetId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.VPC_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceVpcId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.INTERFACE_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.OWNER_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceOwnerId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.AVAILABILITY_ZONE.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceAvailabilityZone());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.REQUESTER_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceRequesterId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.REQUESTER_MANAGED.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceRequesterManaged());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.MAC_ADDRESS.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceMacAddress());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.PRIVATE_DNS_NAME.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfacePrivateDnsName());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.SOURCE_DEST_CHECK.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceSourceDestinationCheck());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.GROUP_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceGroupId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.GROUP_NAME.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceGroupName());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_ATTACHMENT_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceAttachmentId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_INSTANCE_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceInstanceId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_INSTANCE_OWNER_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceInstanceOwnerId());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ADDRESSES_PRIVATE_IP_ADDRESS.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfacePrivateIpAddress());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_DEVICE_INDEX.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceDeviceIndex());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_ATTACH_TIME.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceAttachTime());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_DELETE_ON_TERMINATION.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceDeleteOnTermination());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ADDRESSES_PRIMARY.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceAddressesPrimary());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ADDRESSES_ASSOCIATION_PUBLIC_IP.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfacePublicIp());
        updateFiltersMapEntry(filtersMap, NetworkInterfaceFilter.ADDRESSES_ASSOCIATION_IP_OWNER_ID.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceIpOwnerId());
    }

    void updateImageFiltersMap(ImageInputs imageInputs, Multimap<String, String> filtersMap, String delimiter) {
        setImageRelevantFilters(imageInputs, filtersMap);
        setTagFilters(imageInputs, filtersMap, delimiter);

        updateFiltersMapEntry(filtersMap, CommonFilter.KERNEL_ID.getValue(), imageInputs.getCustomInputs().getKernelId());
        updateFiltersMapEntry(filtersMap, CommonFilter.OWNER_ALIAS.getValue(), imageInputs.getCustomInputs().getOwnerAlias());
        updateFiltersMapEntry(filtersMap, CommonFilter.OWNER_ID.getValue(), imageInputs.getCustomInputs().getOwnerId());
        updateFiltersMapEntry(filtersMap, CommonFilter.RAMDISK_ID.getValue(), imageInputs.getCustomInputs().getRamdiskId());
        updateFiltersMapEntry(filtersMap, CommonFilter.ROOT_DEVICE_NAME.getValue(), imageInputs.getCustomInputs().getRootDeviceName());
        updateFiltersMapEntry(filtersMap, CommonFilter.STATE_REASON_CODE.getValue(), imageInputs.getCustomInputs().getStateReasonCode());
        updateFiltersMapEntry(filtersMap, CommonFilter.STATE_REASON_MESSAGE.getValue(), imageInputs.getCustomInputs().getStateReasonMessage());

        updateFiltersMapEntry(filtersMap, BlockDeviceMappingFilter.DEVICE_NAME.getValue(), imageInputs.getCustomInputs().getBlockMappingDeviceName());
        updateFiltersMapEntry(filtersMap, BlockDeviceMappingFilter.SNAPSHOT_ID.getValue(), imageInputs.getCustomInputs().getBlockDeviceMappingSnapshotId());

        updateFiltersMapEntry(filtersMap, ImageFilter.DESCRIPTION.getValue(), imageInputs.getDescription());
        updateFiltersMapEntry(filtersMap, ImageFilter.ID.getValue(), imageInputs.getCustomInputs().getImageId());
        updateFiltersMapEntry(filtersMap, ImageFilter.MANIFEST_LOCATION.getValue(), imageInputs.getManifestLocation());
    }

    private void setInstanceRelevantFilters(InstanceInputs instanceInputs, Multimap<String, String> filtersMap) {
        if (!Constants.Miscellaneous.NOT_RELEVANT_INT_CODE.equals(instanceInputs.getInstanceStateCode())) {
            updateFiltersMapEntry(filtersMap, InstanceFilter.INSTANCE_STATE_CODE.getValue(), instanceInputs.getInstanceStateCode());
        }

        addFiltersMapRelevantEntry(filtersMap, CommonFilter.ARCHITECTURE.getValue(), instanceInputs.getCustomInputs().getArchitecture());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.HYPERVISOR.getValue(), instanceInputs.getCustomInputs().getHypervisor());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PLATFORM.getValue(), instanceInputs.getCustomInputs().getPlatform());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.VIRTUALIZATION_TYPE.getValue(), instanceInputs.getCustomInputs().getVirtualizationType());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.ROOT_DEVICE_TYPE.getValue(), instanceInputs.getCustomInputs().getRootDeviceType());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PRODUCT_CODE.getValue(), instanceInputs.getCustomInputs().getProductCode());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PRODUCT_CODE_TYPE.getValue(), instanceInputs.getCustomInputs().getProductCodeType());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.INSTANCE_TYPE.getValue(), instanceInputs.getCustomInputs().getInstanceType());

        addFiltersMapRelevantEntry(filtersMap, BlockDeviceMappingFilter.STATUS.getValue(), instanceInputs.getCustomInputs().getBlockDeviceMappingStatus());
        addFiltersMapRelevantEntry(filtersMap, BlockDeviceMappingFilter.DELETE_ON_TERMINATION.getValue(), instanceInputs.getCustomInputs().getDeleteOnTermination());

        addFiltersMapRelevantEntry(filtersMap, InstanceFilter.TENANCY.getValue(), instanceInputs.getTenancy());
        addFiltersMapRelevantEntry(filtersMap, InstanceFilter.INSTANCE_STATE_NAME.getValue(), instanceInputs.getInstanceStateName());
        addFiltersMapRelevantEntry(filtersMap, InstanceFilter.MONITORING_STATE.getValue(), instanceInputs.getMonitoringState());
        addFiltersMapRelevantEntry(filtersMap, InstanceFilter.SOURCE_DESTINATION_CHECK.getValue(),
                instanceInputs.getSourceDestinationCheck());

        addFiltersMapRelevantEntry(filtersMap, NetworkInterfaceFilter.ATTACHMENT_STATUS.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceAttachmentStatus());
        addFiltersMapRelevantEntry(filtersMap, NetworkInterfaceFilter.STATUS.getValue(), instanceInputs.getNetworkInputs().getNetworkInterfaceStatus());
    }

    private void setImageRelevantFilters(ImageInputs imageInputs, Multimap<String, String> filtersMap) {
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.ARCHITECTURE.getValue(), imageInputs.getCustomInputs().getArchitecture());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PRODUCT_CODE_TYPE.getValue(), imageInputs.getCustomInputs().getProductCodeType());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.ROOT_DEVICE_TYPE.getValue(), imageInputs.getCustomInputs().getRootDeviceType());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PLATFORM.getValue(), imageInputs.getCustomInputs().getPlatform());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.PRODUCT_CODE.getValue(), imageInputs.getCustomInputs().getProductCode());
        addFiltersMapRelevantEntry(filtersMap, CommonFilter.HYPERVISOR.getValue(), imageInputs.getCustomInputs().getHypervisor());

        addFiltersMapRelevantEntry(filtersMap, BlockDeviceMappingFilter.VOLUME_SIZE.getValue(), imageInputs.getCustomInputs().getVolumeSize());
        addFiltersMapRelevantEntry(filtersMap, BlockDeviceMappingFilter.VOLUME_TYPE.getValue(), imageInputs.getCustomInputs().getVolumeType());
        addFiltersMapRelevantEntry(filtersMap, BlockDeviceMappingFilter.DELETE_ON_TERMINATION.getValue(), imageInputs.getCustomInputs().getDeleteOnTermination());

        addFiltersMapRelevantEntry(filtersMap, ImageFilter.TYPE.getValue(), imageInputs.getCustomInputs().getVolumeType());
        addFiltersMapRelevantEntry(filtersMap, ImageFilter.IS_PUBLIC.getValue(), imageInputs.getIsPublic());
        addFiltersMapRelevantEntry(filtersMap, ImageFilter.STATE.getValue(), imageInputs.getState());
        addFiltersMapRelevantEntry(filtersMap, ImageFilter.NAME.getValue(), imageInputs.getImageName());
    }

    private void addFiltersMapRelevantEntry(Multimap<String, String> filtersMap, String filterKey, String filterValue) {
        if (StringUtils.isNotBlank(filterValue) && !Constants.Miscellaneous.NOT_RELEVANT.equalsIgnoreCase(filterValue)) {
            filtersMap.put(filterKey, filterValue);
        }
    }

    private void updateFiltersMapEntry(Multimap<String, String> map, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            map.put(key, value);
        }
    }

    private <T> void setTagFilters(T inputs, Multimap<String, String> filtersMap, String delimiter) {
        String[] tagKeys = null;
        String[] tagValues = null;

        if (inputs instanceof InstanceInputs) {
            tagKeys = InputsUtil.getStringsArray(((InstanceInputs) inputs).getCustomInputs().getKeyTagsString(), Constants.Miscellaneous.EMPTY, delimiter);
            tagValues = InputsUtil.getStringsArray(((InstanceInputs) inputs).getCustomInputs().getValueTagsString(), Constants.Miscellaneous.EMPTY, delimiter);
        } else if (inputs instanceof ImageInputs) {
            tagKeys = InputsUtil.getStringsArray(((ImageInputs) inputs).getCustomInputs().getKeyTagsString(), Constants.Miscellaneous.EMPTY, delimiter);
            tagValues = InputsUtil.getStringsArray(((ImageInputs) inputs).getCustomInputs().getValueTagsString(), Constants.Miscellaneous.EMPTY, delimiter);
        }

        updateTagFilters(filtersMap, tagKeys, tagValues);
    }

    private void updateTagFilters(Multimap<String, String> filtersMap, String[] tagKeys, String[] tagValues) {
        if (tagKeys != null && tagValues != null) {
            if (tagKeys.length != tagValues.length) {
                throw new RuntimeException(Constants.ErrorMessages.TAG_KEYS_TAG_VALUES_MISMATCH);
            }

            for (int counter = 0; counter < tagKeys.length - 1; counter++) {
                filtersMap.put(Constants.Miscellaneous.TAG, tagKeys[counter] + Constants.Miscellaneous.EQUAL + tagValues[counter]);
            }
        }
    }
}