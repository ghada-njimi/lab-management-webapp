package com.member.beans;
	
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(using = PublicationBeanDeserializer.class)

public class PublicationBean {

	 @JsonProperty("id")  // ← Ajoutez cette annotation
	 private Long id;
	private String titre;
	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateApparition;

	private String lien;
	private String sourcePdf;
}