/**
 * @author 郭海波
 * @ClassName Base
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午7:09:47
 * @Modify 修改时间
 */
package cn.qm.template;

public interface Base {
	boolean isContentViewIdAnnotationPresent();

	int getContentViewId();

	void viewIdAllInject();

	void methodAllInject();

	void eventbus();

	void uneventbus();

	boolean isEventBusAnnotationPresent();
}
