package ar.com.cipres.model;

import java.util.List;

public class ObjectPaging {
	
	private Long total_items;
	private List<Object> objectList;
	
	public ObjectPaging(List<Object> objectList,Long total_items ){
		this.setTotal_items(total_items);
		this.setObjectList(objectList);		
	}
	
	public ObjectPaging(){
				
	}
	
	public Long getTotal_items() {
		return total_items;
	}
	public void setTotal_items(Long total_items) {
		this.total_items = total_items;
	}
	public List<Object> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}
	
	
	

}
