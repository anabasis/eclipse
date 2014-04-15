package com.slug.web;

/**
 * @(#) FormEntity.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

/**
 * Browser���� ��۹��� multipart/form-data�� parsing��
 * ������ FormEntry Object�� �־ Vector�� ���Ѵ�.
 */
public class FormEntry {
    /**
     * ��۹��� FORM DATA�� �̸�
     */
    private String name;

    /**
     * ��۹��� FORM DATA�� ��
     */
    private String value;

    /**
     * ��۹��� FORM DATA�� FILE�� ��� File name.
     * FILE�� �ƴҰ��� ���ʿ���
     */
    private String fileName;

    /**
     * ��۹��� FORM DATA�� Content Type
     */
    private String contentType;

    /**
     * ������ ����� File�̸�
     */
    private String savedFileName;

    /**
     * ��۹��� File�� ũ��
     */
    private long fileLength;
    /**
     * �Էµ� FORM DATA�� ������ �����ϱ� ���Ͽ� ���ο� Instance�� �����.
     */
    public FormEntry() {
    }
    /**
     * �Էµ� FORM DATA�� ������ �����ϱ� ���Ͽ� ���ο� Instance�� �����.
     * ��� name�� ���� �����Ѵ�.
     *
     * @param name �Էµ� FORM DATA�� TYPE NAME
     */
    public FormEntry( String name ) {
        this.name = name;
    }
    /**
     * value�� ���� �߰��Ѵ�.
     *
     * @param value value
     * @return ����
     */
    public void appendValue(String value) {
        if ( this.value == null ) {
            this.value = value;
        } else {
            this.value += value;
        }
    }
    /**
     * filelength�� return�Ѵ�.
     *
     * @return File Length�� long��
     */
    public long getFileLength() {
        return fileLength;
    }
    /**
     * filename�� return�Ѵ�.
     *
     * @return filename�� String��
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * name���� return�Ѵ�.
     *
     * @return name�� String��
     */
    public String getName() {
        return name;
    }
    /**
     * savedFileName���� return�Ѵ�.
     *
     * @return ������ ����� FileName�� String�� ( Full path )
     */
    public String getSavedFileName() {
        return savedFileName;
    }
    /**
     * value���� return�Ѵ�.
     *
     * @return value�� String��
     */
    public String getValue() {
        return value;
    }
    /**
     * File Size�� ���� ������Ų��.
     * Buffer�� ũ�⿡ ��� ���� Line���� �������� �� �����Ƿ�
     * �߰��� �߻��ϴ� ũ�⸦ ��� ������Ų��.
     *
     * @param fileLength fileLength
     * @return ����
     */
    public void incFileLength(int fileLength ) {
        this.fileLength += fileLength;
    }
    /**
     * Content Type�� ���� �����Ѵ�.
     *
     * @param contentType content Type
     * @return ����
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    /**
     * File Name�� ���� �����Ѵ�.
     *
     * @param fileName File Name
     * @return ����
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * name�� ���� �����Ѵ�.
     *
     * @param name name
     * @return ����
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * ������ ������  File Name �� ���� �����Ѵ�.
     *
     * @param savedFileName savedFileName
     * @return ����
     */
    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }
    /**
     * value�� ���� �����Ѵ�.
     *
     * @param value value
     * @return ����
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * Ŭ�������� ����ϴ� Instance Variable�� ���� ��� �����ش�.
     *
     * @return {}�� �����ִ� ��� Variable�� ��.
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
