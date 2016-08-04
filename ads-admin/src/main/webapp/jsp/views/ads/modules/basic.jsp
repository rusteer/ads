<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="id">ID</label>
	<div class="controls">
		<input class="span9" type="text" id="id" name="id" value="${entity.id}" readonly />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="advertiserId">广告主</label>
	<div class="controls">
		<select class="span9" id="advertiserId" name="advertiserId">
				<option value="0"></option>
				<c:forEach var="element" items="${advertiserList}">
					 <option value="${element.id}" ${entity.advertiserId==element.id?"selected":""}>${element.name}</option>
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
	<label class="control-label" for="price">价格</label>
	<div class="controls">
		<input class="span9" type="text" id="price" name="price" value="${entity.price}" />
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="alias">别名</label>
	<div class="controls">
		<input class="span9" type="text" id="alias" name="alias" value="${entity.alias}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="installable">可安装</label>
	<div class="controls">
		<select class="span9" id="advertiserId" name="installable">
				<option value="0">否</option>
				<option value="1" ${entity.installable?"selected":""}>是</option>
			 
		</select>
	</div>
</div>		
<div class="control-group">
	<label class="control-label" for="url">链接地址</label>
	<div class="controls">
		<input class="span9"  type="text" id="url" name="url" value="${entity.url}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="iconUrl">图标地址</label>
	<div class="controls">
		<input class="span9" type="text" id="iconUrl" name="iconUrl" value="${entity.iconUrl}" />
	</div>
</div>		
<div class="control-group">
	<label class="control-label" for="screenUrl">截图地址</label>
	<div class="controls">
		<input class="span9" type="text"   id="screenUrl" name="screenUrl" value="${entity.screenUrl}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="packageName">包名</label>
	<div class="controls">
		<input class="span9" type="text"  id="packageName" name="packageName" value="${entity.packageName}" />
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