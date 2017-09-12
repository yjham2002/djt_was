

var RBaseElementObject = Class.create({

	initialize :

		function(rhForm,key)
		{
			this.rhForm			= rhForm ;
			this.orgForm		= this.rhForm.getOrg() ;
			this.orgElements	= WPUtil.safeArray(this.orgForm[key]) ;
			this.evOrgElement	= undefined ;
			this.length			= this.orgElements.length	;

			this.rhForm.oBody.wrapElements(this.orgElements) ;

		},

	//V1.0 iterator �ݺ�ó����
	// iterFn �� false �� �����ϸ� �ߴ�
	iter :

		function(iterFn)
		{
			for(var i = 0 ; i < this.orgElements.length ; i++ )
			{
				rv = iterFn(this.orgElements[i],i) ;

				if(rv != undefined && !rv ) 
					break ;
			}
		},

	// ���� DOM ��ü�� �迭 ���·� �����Ѵ�.
	// indes : ù ��° ���ڰ� ������ , index Element �� ����
	getOrg : 

		function()
		{
			return this.orgElements ;

		},

	// ���� DOM ��ü�� �迭�� ù��° ��Ҹ� �����Ѵ�.
	getaOrg :

		function()
		{
			return this.getOrg()[0] ;

		},

	// �̺�Ʈ �߻��Ǿ��� ����� �������� ������Ʈ(DOM) ��ü
	getOrgEv :

		function()
		{
			return this.evOrgElement ;			
		},

	// �Ӽ� �� ��������
	getA :
		function (name,index)
		{
			var array = [] ;
			var obj = null ;

			this.iter(
				function(item,cur) {

					if( index == undefined ) 
						array.push(item.getA(name)) ;
					else if( index == cur )	{
						obj = item.getA(name) ;
						return false ;
					}
				}
			) ;

			if( index == undefined )
				return array		;
			else
				return obj	;
		},

	// ��Ÿ�� �� ��������
	getS :

		function(name,index)
		{
			var array = [] ;
			var obj = null ;

			this.iter(
				function(item,cur) {

					if( index == undefined ) 
						array.push(item.getS(name)) ;
					else if( index == cur )	{
						obj = item.getS(name) ;
						return false ;
					}
				}
			) ;

			if( index == undefined )
				return array		;
			else
				return obj	;

		},

	// �ؽ�Ʈ �� ��������
	getT :

		function(index)
		{
			var array = [] ;
			var obj = null ;

			this.iter(
				function(item,cur) {

					if( index == undefined ) 
						array.push(item.getT()) ;
					else if( index == cur )	{
						obj = item.getT() ;
						return false ;
					}
				}
			) ;

			if( index == undefined )
				return array		;
			else
				return obj	;

		},

	// value �� ��������
	getV :

		function(index)
		{
			var array = [] ;
			var obj = null ;

			this.iter(
				function(item,cur) {

					if( index == undefined ) 
						array.push(item.getV()) ;
					else if( index == cur )	{
						obj = item.getV() ;
						return false ;
					}
				}
			) ;

			if( index == undefined )
				return array		;
			else
				return obj	;

		},


	// �Ӽ� �� �����ϱ�
	setA :

		function(name,value,index)
		{
			this.iter(
				function(item,cur) {
					if( index == undefined )
						item.setA(name,value) ;
					else if( cur == index )
						item.setA(name,value) ;
				}
			) ;
			
		},

	// ��Ÿ�� �� �����ϱ�
	setS :

		function(name,value,index)
		{
			this.iter(
				function(item,cur) {
					if( index == undefined )
						item.setS(name,value) ;
					else if( cur == index )
						item.setS(name,value) ;
				}
			) ;

		},

	// �ؽ�Ʈ �� ����
	setT :

		function(value,index)
		{
			var type = ( typeof(value) == "string" ) ? "string" : "array" ;

			this.iter(
				function(item,cur) {

					rvalue = ( type == "string" ) ? value : value[cur] ;

					if( index == undefined )
						item.setT(rvalue) ;
					else if( cur == index )
						item.setT(rvalue) ;
				}
			) ;
		},

	// �ؽ�Ʈ �� ����
	// index ��� ����
	// value : String , Array 
	setV :

		function(value,index)
		{
			var type = ( typeof(value) == "string" ) ? "string" : "array" ;

			this.iter(
				function(item,cur) {

					rvalue = ( type == "string" ) ? value : value[cur] ;

					if( index == undefined )
						item.setV(rvalue) ;
					else if( cur == index )
						item.setV(rvalue) ;
				}
			) ;
		},

	// üũ�ڽ� , ������ ������ �޼ҵ�
	// setCheck(index)
	setC :

		function(index)
		{
			this.iter(
				function(item,cur)
				{	
					if( index == cur )
						item.checked = true ;
				}
			) ;
		},


	// �����ڸ� �������� ���� �迭�� ������ ���� ������ �� �ְ� �Ѵ�.
	// �Ǵ� �����ڰ� ���� ��� eValue �� ���� �����Ѵ�.
	setVBySpliter :

		function(eValue,spliter)
		{
			var aValue = ( spliter != undefined ) ? eValue.split(spliter) : new Array() ;

			this.iter(
				function(item)
				{
					item.value = ( spliter == undefined ) ? eValue : aValue[i] ;
				}
			) ;

		},

	setOrgEv :

		function(ele)
		{
			this.evOrgElement = ele ;
		},


	// ��ü ��� ���� ����
	able :

		function(isAble)
		{
			this.iter(
				function(item) {
					item.able(isAble) ;
				}
			) ;
		},

	// �б� �������� �Ұ�����
	readable :

		function(isAble)
		{
			this.iter(
				function(item) {
					item.readable(isAble) ;
				}
			) ;
		},

	// ��ü ����� V0.5
	// true/false ������ ����
	hide :

		function(isRendering)
		{
			this.iter(
				function(item) {
					item.hide(isRendering) ;
				}
			) ;
		},


	// ��ü ���̱� V0.5
	// true/false ������ ����
	show :

		function(isRendering)
		{
			this.iter(
				function(item) {
					item.show(isRendering) ;
				}
			) ;
		}

	


}) ;


// overloading getOrg(index) 
/*
Class.overload.apply(RBaseElementObject,[ 'getOrg',[ String ] , 
	function(index)
	{
			return this.getOrg()[index] ;

	} ]
) ;

*/


