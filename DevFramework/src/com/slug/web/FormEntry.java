package com.slug.web;

/**
 * @(#) FormEntity.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

/**
 * Browser에서 전송받은 multipart/form-data를 parsing한
 * 정보를 FormEntry Object에 넣어서 Vector로 관리한다.
 */
public class FormEntry {
    /**
     * 전송받은 FORM DATA의 이름
     */
    private String name;

    /**
     * 전송받은 FORM DATA의 값
     */
    private String value;

    /**
     * 전송받은 FORM DATA가 FILE일 경우 File name.
     * FILE이 아닐경우는 불필요함
     */
    private String fileName;

    /**
     * 전송받은 FORM DATA의 Content Type
     */
    private String contentType;

    /**
     * 서버에 저장될 File이름
     */
    private String savedFileName;

    /**
     * 전송받은 File의 크기
     */
    private long fileLength;
    /**
     * 입력된 FORM DATA의 정보를 저장하기 위하여 새로운 Instance를 만든다.
     */
    public FormEntry() {
    }
    /**
     * 입력된 FORM DATA의 정보를 저장하기 위하여 새로운 Instance를 만든다.
     * 생성시 name에 값을 세팅한다.
     *
     * @param name 입력된 FORM DATA의 TYPE NAME
     */
    public FormEntry( String name ) {
        this.name = name;
    }
    /**
     * value에 값을 추가한다.
     *
     * @param value value
     * @return 없음
     */
    public void appendValue(String value) {
        if ( this.value == null ) {
            this.value = value;
        } else {
            this.value += value;
        }
    }
    /**
     * filelength를 return한다.
     *
     * @return File Length의 long값
     */
    public long getFileLength() {
        return fileLength;
    }
    /**
     * filename을 return한다.
     *
     * @return filename의 String값
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * name값을 return한다.
     *
     * @return name의 String값
     */
    public String getName() {
        return name;
    }
    /**
     * savedFileName값을 return한다.
     *
     * @return 서버에 저장될 FileName의 String값 ( Full path )
     */
    public String getSavedFileName() {
        return savedFileName;
    }
    /**
     * value값을 return한다.
     *
     * @return value의 String값
     */
    public String getValue() {
        return value;
    }
    /**
     * File Size의 값을 증가시킨다.
     * Buffer의 크기에 따라서 여러 Line으로 나누어질 수 있으므로
     * 추가로 발생하는 크기를 계속 증가시킨다.
     *
     * @param fileLength fileLength
     * @return 없음
     */
    public void incFileLength(int fileLength ) {
        this.fileLength += fileLength;
    }
    /**
     * Content Type에 값을 설정한다.
     *
     * @param contentType content Type
     * @return 없음
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    /**
     * File Name에 값을 설정한다.
     *
     * @param fileName File Name
     * @return 없음
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * name에 값을 설정한다.
     *
     * @param name name
     * @return 없음
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 서버에 저장할  File Name 의 값을 설정한다.
     *
     * @param savedFileName savedFileName
     * @return 없음
     */
    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }
    /**
     * value에 값을 설정한다.
     *
     * @param value value
     * @return 없음
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * 클래스에서 사용하는 Instance Variable의 값을 모두 보여준다.
     *
     * @return {}로 묶여있는 모든 Variable의 값.
     * name, value, fileName, contentType, savedFileName, fileLength
     */
    public String toString() {
        return  "{" +
                name + "," +
                value + "," +
                fileName + "," +
                contentType + "," +
                savedFileName + "," +
                fileLength +
                "}";
    }
}
