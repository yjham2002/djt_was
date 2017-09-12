// Text Element Wrapping �� Class

var RHCheckBox = Class.create(RBaseElementObject,{

	initialize :

		function($super,rhForm,key)
		{
			$super(rhForm,key) ;
		},

	// üũ �Ǿ����� ���� ����
	// return : Array<boolean>
	getC : 

		function(index)
		{
			var array = [] ;

			this.iter(
				function(item,cur)
				{
					if( index == undefined ) 
						array.push(item.checked) ;
					else if( index == cur )
						array.push(item.checked) ;
				}
			) ;

			return array ;
		},

	// üũ�� �� ��������	
	// return : Array<string>
	getV :

		function()
		{
			var array = [] ;

			this.iter(
				function(item,cur)
				{
					if( item.checked )
						array.push(item.getV()) ;
				}
			) ;

			return array ;
		},

	// �� üũ�ϱ�
	// return : this 
	setV :

		function(value)
		{
			this.iter(
				function(item,cur) {

					if( item.getV() == value )
						item.checked = true ;
				}
			) ;

			return this ;
		},


	// ����/���� �ϱ�
	// return : this 
	doCheck :

		function(is)
		{
			this.iter(
				function(item,cur)
				{
					item.checked = is ;
				}
			) ;

			return this ;
		},

	// Ÿ�� Radio Checked ����ó���ϱ�
	// return : this 
	doCheckChain :

		function(target,isReverse)
		{
			isReverse = ( isReverse == undefined ) ? false : true ;

			var tChecked = this.getaOrg().checked ;

			tChecked = ( isReverse == true ) ? !tChecked : tChecked ;

			target.doCheck(tChecked) ;

			return this ;
		},

	// üũ �� üũ�ڽ� ���� ��������
	// return : number 
	getCheckedCount : 

		function()
		{
			var cnt = 0 ;

			this.iter(
				function(item,cur)
				{
					cnt += ( item.checked ) ? 1 : 0 ;
				}
			) ;

			return cnt ;
		},

	// ���� ��ư ó�� ����ó�� �ϱ�
	// return : this 	
	radiolize :

		function()
		{

			this.rhForm.oBody.attachEvent(this.getOrg(),"onclick",this.radiolizeHandler) ;

			return this ;
		},

	// ���� ��ư ó�� ����ó���� ���� Ŭ�� �ڵ鷯
	radiolizeHandler :

		function(str)
		{
			var eventEle = this.getOrgEv() ;

			this.iter(
				function(item,index)
				{
					item.checked = ( item == eventEle )	;
				}
			) ;
		}



}) ;


