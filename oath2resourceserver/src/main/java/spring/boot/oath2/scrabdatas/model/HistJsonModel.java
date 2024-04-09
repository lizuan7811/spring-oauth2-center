package spring.boot.oath2.scrabdatas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HistJsonModel{
	@JsonProperty("stat")
	private String stat;
	@JsonProperty("date")
	private String date;
	@JsonProperty("title")
	private String title;
	@JsonProperty("fields")
	private List<String> fields;
	@JsonProperty("data")
	private List<List<String>> data;
	@JsonProperty("notes")
	private List<String> notes;
	@JsonProperty("total")
	private int total;

}
