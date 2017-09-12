
RHEventUtil = function(){} ;

// �̺�Ʈ ���̱�
RHEventUtil.attachEvent =

	function(oBody,oTarget,eventOfType,handlerOfEvent,fnOfCondition)
	{
		oTarget	=	RHEventUtil.safeArray(oTarget) ;
		
		for(var i = 0 ; i < oTarget.length ; i++ )	{

			oTargetForCondition = RHEventUtil.getEventObj(oBody,oTarget[i]) ;

			if( fnOfCondition == undefined || fnOfCondition.call(oTargetForCondition) == true )
				RHEventUtil.attachEventSingle(oBody,oTarget[i],eventOfType,handlerOfEvent)	;
		}
	}

// obj �� ������ array ���� ��ȯ�Ѵ�. ������ Ÿ���� ���߱� ���� ����Ѵ�.
RHEventUtil.safeArray = 

	function(obj)	{

		
		obj = ( obj == undefined ) ? new Array() : obj ;
		
	
		// select ��ü�� ��ü length �޽�带 ������ �ִ�.
		if( obj.tagName != undefined && obj.tagName.toLowerCase() == "select" )
		{	
			obj = new Array(obj)
		} 
		else
			obj = ( obj.length == undefined ) ? new Array(obj) : obj ;

		return obj ;
	}

RHEventUtil.wrapEventString =

	function(eventOfType)
	{
		return ( RichFrameworkStatic.isIE == false ) ? eventOfType.substring(2) : eventOfType ;

	}

//V1.0 ���ϵ� �̺�Ʈ �� ���� click ���
RHEventUtil.uniqueEventString =
	
	function(eventOfType)
	{

		return ( RichFrameworkStatic.isIE == false ) ? eventOfType : eventOfType.substring(2) ;

	}


// �̺�Ʈ ���̱� ���� ���̵� ��ü ��� ����
RHEventUtil.attachEventSingle =

	function(oBody,oTarget,eventOfType,handlerOfEvent)
	{
		// �̺�Ʈ �̸� ó��
		eventOfType = RHEventUtil.wrapEventString(eventOfType) ;

		if( oTarget == undefined ) return false ;

		if( eventOfType == "onclick" || eventOfType == "click" )
				 oTarget.style.cursor = "pointer"	; 

		try
		{
			 // IE
			 if( jQuery.browser.msie )
				 oTarget.attachEvent(eventOfType,RHEventUtil.prepareHandler.bind(oBody,eventOfType,handlerOfEvent)) ;	
			else	
				oTarget.addEventListener(eventOfType,RHEventUtil.prepareHandler.bind(oBody,eventOfType,handlerOfEvent),false) ;

		}
		catch (e)
		{	
			WPUtil.log("RHEventUtil.attachEventSingle","",e) ;
		}
	}

// V1.0 ���� �̺�Ʈ �߻����� ��ü �����ϱ�
// �̺�Ʈ �ɸ� �±װ� INPUT �±��̸� RichBaseElement �� �����Ѵ�.
RHEventUtil.getEventObj =

	function(oBody,ele)
	{
		var rv = null ;

		if( ele != null )	{

			Object.extend(ele,RichElement) ; 

			if( ele.isInput() ) 
			{	
				var rf = oBody.getForm(ele.getFormNm()) ;
				rv = rf.getE(ele.getNm()) ;
				rv.setOrgEv(ele) ;
			}
			else
				rv = ele ;
		}

		return rv ;
	}

	

// V1.0 �̺�Ʈ �ɱ� ���� �׻� ����Ǵ� �ڵ鷯
RHEventUtil.prepareHandler =

	function(eventOfType,fn)
	{
		eventOfType = RHEventUtil.uniqueEventString(eventOfType) ;

		this.prepareGlobalHandler() ; 

		eventOfType = eventOfType.substring(0,1).toUpperCase() + eventOfType.substring(1) ;
				
		obj = RHEventUtil.getEventObj(this,event.srcElement) ;
		
		// ������ �´� �̺�Ʈ ��ó���� ����
		var isContinue = eval("this.prepare" + eventOfType + "Handler").apply(obj,arguments) ;

		isContinue = ( isContinue == undefined ) ? true : isContinue ;
		
		// ���� �̺�Ʈ �ڵ鷯 ����
		if( isContinue )
			fn.apply(obj,arguments) ;

	}
