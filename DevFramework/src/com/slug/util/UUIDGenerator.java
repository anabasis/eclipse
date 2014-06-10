package com.slug.util;


/**
 * 1. 기능 : UUID Generation Class
 * 2. 처리 개요 :
 * *     -
 * 3. 주의사항
 *
 * @author  :
 * @version : v 1.0.0
 * @see : 관련 기능을 참조
 * @since   :
 * @deprecated :
 */
public final class UUIDGenerator {
    /**
     * 1. 기능 :  Private 생성자
     * 2. 처리 개요 :
     *     -
     * 3. 주의사항
     *     - Instance 생성하지 못함
     **/
    private UUIDGenerator() {
    }

    /**
     * 1. 기능 :  UUID를 생성하여 스트링으로 리턴하는 메쏘드
     * 2. 처리 개요 :
     *     -
     * 3. 주의사항
     *
     * @return 생성된 UUID 스트링
     **/
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
