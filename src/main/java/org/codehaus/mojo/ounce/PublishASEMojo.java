/**
 * (c) Copyright IBM Corporation 2016.
 * (c) Copyright HCL Technologies Ltd. 2019. 
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package org.codehaus.mojo.ounce;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.mojo.ounce.core.OunceCore;
import org.codehaus.plexus.util.StringUtils;


/**
 * This mojo provides the ability to publish an AppScan Source assessment to AppScan
 * Enterprise
 * 
 * @author <a href="mailto:sherardh@us.ibm.com">Sherard Howe</a>
 * 
 */
@Mojo (name="publishASE", aggregator=true)
@Execute( lifecycle="scan", phase=LifecyclePhase.PACKAGE )
public class PublishASEMojo extends AbstractOunceMojo {
	
	
	
	/**
	 * This is the name of the application inside of AppScan Enterprise
	 *
	 * 
	 */
	@Parameter (property="ounce.aseApplication")
	String aseApplication;
	
	/**
	 * This is the name that the assessment will be saved as in the Enterprise Console.
	 *
	 * 
	 */
	@Parameter (property="ounce.name")
	String name;
	
	/**
	 * This is the assessment file that will be published to AppScan Enterprise
	 * 
	 * 
	 */
	@Parameter (property="ounce.assessmentOutput", defaultValue="${basedir}/${project.artifactId}.ozasmt")
	String assessmentOutput;
	
	/**
	 * Optional. Assign a caller to the report generation operation. The caller can be
	 * the name of an actual user, but this is not required. The caller name is written
	 * to the ounceauto log file
	 * 
	 * 
	 */
	@Parameter (property="ounce.caller")
	String caller;
	
	/**
	 * Optional. Enterprise Console folder to publish to. If this argument is not used, the
	 * assessment will be published to your default Enterprise Console folder.
	 * 
	 * 
	 */
	@Parameter (property="ounce.folderID")
	String folderID;
	
	/**
	 * The location of the Ounce client installation directory if the Ounce client is not on the path
	 * 
	 * 
	 */
	@Parameter (property="ounce.installDir")
	String installDir;
	
	/**
     * Forces the goal to wait until the scan finishes, thus blocking the Maven build. This is useful if the scan is
     * being performed from the report mojo as part of integration with the site target and the site is getting
     * deployed.
     * 
     * 
     */
	@Parameter (property="ounce.wait", defaultValue="false")
	boolean waitForScan;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if(StringUtils.isEmpty(assessmentOutput))
		{
			throw new MojoExecutionException("\'assessmentFile\' must be defined.");
		}
		
		try
		{
			OunceCore core = getCore();
			core.publishASE(aseApplication, name, assessmentOutput, caller, folderID, installDir, waitForScan, getLog());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
