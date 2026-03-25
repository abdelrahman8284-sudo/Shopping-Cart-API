package com.abdelrahman.shoppingcart.configurations;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties("app.image-storage")
@Getter
public class ImageProperties {

	private String basePath;
	
	private Set<String> allowedMimeTypes;
	
	public ImageProperties() {
		this.basePath="./images";
		this.allowedMimeTypes = Set.of(
				"image/jpeg",
				"image/png",
				"image/webp",
				"image/gif");
	}
}
