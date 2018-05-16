package jp.wat.basket.common;

import java.io.Serializable;

/**
 * Enum用エンコーダーインターフェース
 *
 * @param <T> Enum定義
 */
public interface Encodable<T extends Serializable> {
	
    /**
     * エンコーダー
     *
     * @return Enum定義
     */
    T getCode();

    /**
     * コードの名称を返す.
     *
     * @return 名称
     */
    String getName();
    
}
