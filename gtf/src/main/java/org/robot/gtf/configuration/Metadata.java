package org.robot.gtf.configuration;

import java.util.HashMap;
import java.util.Map;

public class Metadata {

	private Map<Integer, String> values = new HashMap<Integer, String>();
	
	private String headerTemplateFilePath;
	
	private String footerTemplateFilePath;
	
	
	public void addValue(int pos, String value) {
		values.put(pos, value);
	}

	public String getValue(int pos) {
		return values.get(pos);
	}

	public String getHeaderTemplateFilePath() {
		return headerTemplateFilePath;
	}

	public void setHeaderTemplateFilePath(String headerTemplateFilePath) {
		this.headerTemplateFilePath = headerTemplateFilePath;
	}

	public String getFooterTemplateFilePath() {
		return footerTemplateFilePath;
	}

	public void setFooterTemplateFilePath(String footerTemplateFilePath) {
		this.footerTemplateFilePath = footerTemplateFilePath;
	}
}
