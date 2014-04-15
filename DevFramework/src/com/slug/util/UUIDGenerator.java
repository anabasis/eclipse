package com.slug.util;


/**
 * 1. ��� : UUID Generation Class
 * 2. ó�� ���� :
 * *     -
 * 3. ���ǻ���
 *
 * @author  :
 * @version : v 1.0.0
 * @see : ��� ����� ����
 * @since   :
 * @deprecated :
 */
public final class UUIDGenerator {
    /**
     * 1. ��� :  Private ����
     * 2. ó�� ���� :
     *     -
     * 3. ���ǻ���
     *     - Instance ������ ����
     **/
    private UUIDGenerator() {
    }

    /**
     * 1. ��� :  UUID�� ���Ͽ� ��Ʈ������ �����ϴ� �޽��
     * 2. ó�� ���� :
     *     -
     * 3. ���ǻ���
     *
     * @return ��� UUID ��Ʈ��
     **/
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
