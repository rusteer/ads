<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="pop_enabled">功能启用</label>
	<div class="controls">
		<select class="span9" id="pop_enabled" name="pop_enabled">
				<option value="0">否</option>
				<option value="1" ${pop.enabled?"selected":""}>是</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="pop_startHour">开始时间</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_startHour" name="pop_startHour" value="${pop.startHour>0?pop.startHour:7}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="pop_endHour">结束时间</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_endHour" name="pop_endHour" value="${pop.endHour>0?pop.endHour:23}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="pop_interval">显示间隔(秒)</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_interval" name="pop_interval" value="${pop.interval>0?pop.interval:7200}" />
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="pop_maxCount">显示次数</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_maxCount" name="pop_maxCount" value="${pop.maxCount>0?pop.maxCount:5}" />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="pop_whitePackages">包名白名单</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_whitePackages" name="pop_whitePackages" value="${pop.whitePackages}" />
	</div>
</div>		

<div class="control-group">
	<label class="control-label" for="pop_blackPackages">包名黑名单</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_blackPackages" name="pop_blackPackages" value="${pop.blackPackages}" />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="pop_cancelDelaySeconds">取消延迟加载(秒)</label>
	<div class="controls">
		<input class="span9" type="text" id="pop_cancelDelaySeconds" name="pop_cancelDelaySeconds" value="${pop.cancelDelaySeconds>0?pop.cancelDelaySeconds:5}" />
	</div>
</div>	

	