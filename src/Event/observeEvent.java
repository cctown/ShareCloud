package Event;

import java.util.Observable;

public class observeEvent extends Observable{
	
	public String eventTag;
	// 定义一个私有的构造方法
	private observeEvent() {  
    }
	// 将自身的实例对象设置为一个属性,并加上Static和final修饰符
	private static final observeEvent instance = new observeEvent();
	// 静态方法返回该类的实例
	public static observeEvent getInstance() {  
        return instance;  
    }
	
	public void setEventTag(String tag) {
		this.eventTag = tag;
	    setChanged();  				//标注tag已经被更改
	    notifyObservers(tag);  		//通知观察者数据已被更改
	}
}
