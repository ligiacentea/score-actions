package org.eclipse.score.content.ssh.actions;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType;
import com.hp.oo.sdk.content.plugin.GlobalSessionObject;
import org.eclipse.score.content.ssh.services.actions.SSHShellAbstract;
import org.eclipse.score.content.ssh.services.actions.ScoreSSHShellLogOff;
import org.eclipse.score.content.ssh.entities.SSHConnection;
import org.eclipse.score.content.ssh.entities.SSHShellInputs;
import org.eclipse.score.content.ssh.utils.Constants;

import java.util.Map;

/**
 * @author ioanvranauhp
 *         Date: 11/03/14
 */
public class SSHShellLogoff extends SSHShellAbstract {

    @Action(name = "SSH Shell Logoff",
            outputs = {
                    @Output(Constants.OutputNames.RETURN_CODE),
                    @Output(Constants.OutputNames.RETURN_RESULT),
                    @Output(Constants.OutputNames.EXCEPTION)
            },
            responses = {
                    @Response(text = Constants.ResponseNames.SUCCESS, field = Constants.OutputNames.RETURN_CODE, value = Constants.ReturnCodes.RETURN_CODE_SUCCESS, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED),
                    @Response(text = Constants.ResponseNames.FAILURE, field = Constants.OutputNames.RETURN_CODE, value = Constants.ReturnCodes.RETURN_CODE_FAILURE, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, isOnFail = true)
            }
    )
    public Map<String, String> sshLogoff(
            @Param(Constants.SSH_SESSIONS_DEFAULT_ID) GlobalSessionObject<Map<String, SSHConnection>> globalSessionObject,
            @Param(value = Constants.SESSION_ID, required = true) String sessionId) {

        SSHShellInputs sshShellInputs = new SSHShellInputs();
        sshShellInputs.setSshGlobalSessionObject(globalSessionObject);
        sshShellInputs.setSessionId(sessionId);

        return new ScoreSSHShellLogOff().execute(sshShellInputs);
    }
}