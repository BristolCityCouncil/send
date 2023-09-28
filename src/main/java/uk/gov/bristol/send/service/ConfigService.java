package uk.gov.bristol.send.service;

import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.azure.cosmos.implementation.apachecommons.lang.StringUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

/**
 * The Class ConfigService.
 */
@Service
@EnableConfigurationProperties
public class ConfigService {

	@Value("${file.subtypes}")
	private String subtypeProps;

	@Value("${max.file.size}")
	private long maxFileSize;

	@Value("${allowed.file.types}")
	private String[] allowedFileTypes;

	@Value("${file.uploads.file.location}")
	private String fileLocaton;

	@Value("${file.uploads.file.temp.location}")
	private String tempFileLocaton;

	@Value("${clamav.remote.url}")
	private String clamAVRemoteURL;

	@Value("${clamav.remote.port}")
	private int clamAVRemotePort;
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	@Value("${submission.email.topup.panel}")
	private String submissionEmailTopUpPanel;
	
	@Value("${submission.email.subject}")
	private String submissionEmailSubject;

	@Value("${sharepoint.site.id}")
	private String sharepointSiteId;

	@Value("${sharepoint.drive.id}")
	private String sharepointDriveId;

	@Value("${sharepoint.app.secret}")
	private String sharepointAppSecret;

	@Value("${sharepoint.client.id}")
	private String sharepointClientId;	

	@Value("${sharepoint.sub.folder}")
	private String sharepointSubFolder;	


	/**
	 * Gets the subtypes.
	 *
	 * @return the subtypes
	 */
	public Map<String, String> getSubtypes() {
		Map<String, String> subtypes = Maps.newLinkedHashMap();

		if (subtypeProps != null && !subtypeProps.isEmpty() && !subtypeProps.equals("null")) {
			subtypes = Splitter.on(",").withKeyValueSeparator("=").split(subtypeProps);
		}

		return subtypes;
	}

	public long getMaxFileSize() {
		return maxFileSize;
	}

	public String[] getAllowedFileTypes() {
		return allowedFileTypes;
	}

	public String getClamAVRemoteURL() {
		return clamAVRemoteURL;
	}

	public int getClamAVRemotePort() {
		return clamAVRemotePort;
	}	
		
	public String getEmailFrom() {
		return emailFrom;
	}		

	public String getSubmissionEmailTopUpPanel() {
		return submissionEmailTopUpPanel;
	}

	public String getSubmissionEmailSubject() {
		return submissionEmailSubject;
	}

	public String getSharepointDriveId(){
		return sharepointDriveId;
	}

	public String getSharepointSiteId(){
		return sharepointSiteId;
	}

	public String getSharepointAppSecret(){
		return sharepointAppSecret;
	}

	public String getSharepointClientId() {
		return sharepointClientId;
	}

	public void setSharepointClientId(String sharepointClientId) {
		this.sharepointClientId = sharepointClientId;
	}

	public String getSharepointSubFolder() {
		return sharepointSubFolder;
	}

	public void setSharepointSubFolder(String sharepointSubFolder) {
		this.sharepointSubFolder = sharepointSubFolder;
	}	

	public String getFileLocaton() {
		if (StringUtils.isEmpty(fileLocaton)) {
			if (StringUtils.isEmpty(tempFileLocaton)) {
				String sep = System.getProperty("file.separator");
				return Path.of(System.getProperty("java.io.tmpdir")) + sep;
			} else {
				return tempFileLocaton;
			}
		} else {
			return fileLocaton;
		}
	}

}
