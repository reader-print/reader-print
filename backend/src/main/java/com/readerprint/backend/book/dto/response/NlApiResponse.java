package com.readerprint.backend.book.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JacksonXmlRootElement(localName = "root")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class NlApiResponse {

    @JacksonXmlProperty(localName = "paramData")
    private ParamData paramData;

    @JacksonXmlElementWrapper(localName = "result")
    @JacksonXmlProperty(localName = "item")
    private List<Item> items;

    public int getTotal() {
        return paramData != null ? paramData.getTotal() : 0;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    public static class ParamData {
        private String kwd;
        private int total;
        private int pageNum;
        private int pageSize;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    public static class Item {
        @JacksonXmlProperty(localName = "title_info")
        private String titleInfo;

        @JacksonXmlProperty(localName = "author_info")
        private String authorInfo;

        @JacksonXmlProperty(localName = "pub_info")
        private String pubInfo;

        @JacksonXmlProperty(localName = "pub_year_info")
        private String pubYearInfo;

        private String isbn;

        @JacksonXmlProperty(localName = "kdc_name_1s")
        private String category;

        @JacksonXmlProperty(localName = "type_name")
        private String typeName;

        @JacksonXmlProperty(localName = "doc_yn")
        private String docYn;

        @JacksonXmlProperty(localName = "org_link")
        private String orgLink;

        @JacksonXmlProperty(localName = "detail_link")
        private String detailLink;

        @JacksonXmlProperty(localName = "control_no")
        private String controlNo;
    }
}
