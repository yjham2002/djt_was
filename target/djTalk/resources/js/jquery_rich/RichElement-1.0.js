
var RichElement = { 

	// �� ������ �ʵ� ����/�ƴ���
	isInput :
		function()
		{
			return ( this.tagName != undefined && this.tagName.toLowerCase() == "input" ) ;
		},

	// �� �̸� �������� 
	getFormNm :
		function()
		{
			return this.form.name ;
		},
	
	// ������Ʈ �̸� ��������
	getNm :
		function()
		{
			return this.name ;
		},

	// ������Ʈ�� Ÿ���� �����´�.
	getType : 
		function()
		{
			return  this.type ;
		},
	
	// �Ӽ� �� ��������
	getA :
		function (name)
		{
			return this.getAttribute(name) ;
		},

	// ��Ÿ�� �� ��������
	getS :

		function(name)
		{
			return this.style[name] ;
		},

	// �ؽ�Ʈ �� ��������
	getT :

		function()
		{
			var type = this.getType() ;

			if( type == "select-one" )
				return this.options(this.selectedIndex) ;
			else if( this.innerHTML ) 
				return this.innerHTML ;
			else 
				return this.value ;
		},

	// value �� ��������
	getV :

		function()
		{
			return this.value ;

		},


	// �Ӽ� �� �����ϱ�
	setA :

		function(name,value)
		{	
			this.setAttribute(name,value) ;
		},

	// ��Ÿ�� �� �����ϱ�
	setS :

		function(name,value)
		{
			this.style[name] = value ;
		},

	// �ؽ�Ʈ �� ����
	setT :

		function(value)
		{	if( this.innerHTML ) 
				this.innerHTML = value ;	
			else
				this.value = value ;
		},

	// �ؽ�Ʈ �� ����
	setV :

		function(value)
		{	
			this.value = value ;
		},


	// ��ü ��� ���� ����
	able :

		function(isAble)
		{
			this.disabled = isAble ;
		},

	// �б� �������� �Ұ�����
	readable :

		function(isAble)
		{
			this.readOnly = isAble ;
		},

	// ��ü ����� V0.5
	// true/false ������ ����
	hide :

		function(isRendering)
		{
			if( isRendering == true )
				this.setS('visibility','hidden')	;
			else
				this.setS('display','none')			;
		},


	// ��ü ���̱� V0.5
	// true/false ������ ����
	show :

		function(isRendering)
		{
			if( isRendering == true )
				this.setStyle('visibility','visible')	;
			else
				this.setStyle('display','inline')		;
		}

} ;


// Class Aliasing 
Object.extend(RichElement,{

	get : RichElement.getA,
	set : RichElement.setA

}) ;


