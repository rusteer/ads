<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="id">ID</label>
	<div class="controls">
		<input class="span9" type="text" id="id" name="id" value="${entity.id}" readonly />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="cpId">CP</label>
	<div class="controls">
		<select class="span9" id="cpId" name="cpId">
				<option value="0"></option>
				<c:forEach var="element" items="${cpList}">
					 <option value="${element.id}" ${entity.cpId==element.id?"selected":""}>${element.name}</option>
				</c:forEach>
		</select>
	</div>
</div>		
<div class="control-group">
	<label class="control-label" for="name">名称</label>
	<div class="controls">
		<input class="span9" type="text" id="name" name="name" value="${entity.name}" />
	</div>
</div>
 
<div class="control-group">
	<label class="control-label" for="description">简介</label>
	<div class="controls">
		<textarea class="span9" id="description" name="description">${entity.description}</textarea>
	</div>
</div>				
<div class="control-group">
	<label class="control-label" for="enabled">状态</label>
	<div class="controls">
		<select class="span9" id="enabled" name="enabled">
				<option value="0">关闭</option>
				<option value="1" ${entity.enabled?"selected":""}>开启</option>
			 
		</select>
	</div>
</div>