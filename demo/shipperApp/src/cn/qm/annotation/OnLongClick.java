/**
 * @author 郭海波
 * @ClassName onTextWatch
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月12日 上午11:50:43
 * @Modify 修改时间
 */
package cn.qm.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Event(listenerType = View.OnLongClickListener.class, listenerSetter = "setOnLongClickListener", methodName = "onLongClick")
public @interface OnLongClick {
	int value();
}
