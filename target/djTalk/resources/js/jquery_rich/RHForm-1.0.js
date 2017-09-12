
var RHForm = Class.create({

	// ��ü �ʱ�ȭ V0.7
	initialize :

		function(oBody,orgForm,submitOfType)
		{
			this.oBody = oBody	;
			
			this.orgForm = orgForm ;

			this.el		= orgForm.elements			// type=image �� ���ܵ� elements
			
			if( this.orgForm.method == "" )
				this.orgForm.method = "POST" ;
				
			this.preAction = this.orgForm.action  ;
			this.preTarget = this.orgForm.target ;
			this.preMethod = this.orgForm.method ;

			this.submitOfType = ( submitOfType == undefined ) ? "NORMAL" : submitOfType ; // AJAX
			this.submitOfSuper = null ;

			this.isSubmited = false ;		// �ߺ� Submit ����

			return this ;
		},


	setAction :

		function(url)
		{
			this.preAction = this.orgForm.action ; 
			this.orgForm.action = url ;

			return this ;
		},
		
	setMethod :

		function(str)
		{
			this.preMethod = this.orgForm.method ; 
			this.orgForm.method = str ;

			return this ;
		},
		
	setTarget :

		function(target)
		{
			this.preTarget = this.orgForm.target ; 
			this.orgForm.target = target ;

			return this ;
		},


	setSubmitType :

		function(key)
		{
			this.submitOfSuper = key ;

			return this ;
		},

	getSubmitType : 

		function()
		{
			return this.submitOfSuper ;

			return this ;
		},

	// �������� �� ����
	getOrg : 

		function()
		{
			return this.orgForm ;

		},

			

	// Document Object ������ Array �� �������� V0.7
	getOrgE : 

		function(key)
		{
			var orgElements = WPUtil.safeArray(this.orgForm[key]) ;

			return this.oBody.wrapElements(orgElements) ;
		},

	// Document Object ������ Array �� �������� V0.7
	getaOrgE : 

		function(key)
		{
			return this.getOrgE(key)[0] ;
		},

	// �� ������Ʈ ����
	getE :

		function(key)
		{

			var type	= this.getaOrgE(key).getType() ;
			var obj		= null ;

			switch(type)
			{
				case "text" :
					obj = new RHText(this,key) ;
					break ;
				case "password" :
					obj = new RHText(this,key) ;
					break ;
				case "textarea" :
					obj = new RHText(this,key) ;
					break ;
				case "hidden" :
					obj = new RHText(this,key) ;
					break ;
				case "button" :
					obj = new RHText(this,key) ;
					break ;
				case "radio" :
					obj = new RHRadio(this,key) ;
					break ;
				case "checkbox" :
					obj = new RHCheckBox(this,key) ;
					break ;
				case "select-one" :
					obj = new RHSelect(this,key) ;
					break ;
				default :
					obj = this.getOrgE(key) ;

			}

			return obj ;

		},

	// ������ �����´�.
	getV : 

		function(key)
		{
			return this.getE(key).getV() ;
		},

	// �� �ؽ�Ʈ�� �����´�.
	getT : 

		function(key)
		{
			return this.getE(key).getT() ;
		},

	// ���� �����迭�� ������ ���� ����
	setVAssoc : 

		function(assoc)
		{
			for (var key in assoc) 
				if(assoc.hasOwnProperty(key))
					this.setV(key,assoc[key]) ;

			return this ;
		},

	// ���� �����Ѵ�.
	setV : 

		function(key,value,index)
		{
	
			this.getE(key).setV(value,index) ;

			return this ;

		},

	// �迭�� ��ü�� ���� �ε����� �����Ѵ�.(For Text �� ����) 
	setVBySpliter : 

		function(key,value,spliter)
		{
			this.getE(key).setVBySpliter(value,spliter) ;

			return this ;
		}, 


	// �Ϲ� SUBMIT ���( POPUP ���� SUBMIT )
	submit : 

		function()
		{
			if( this.validate() == true )	{

				if( this.isSubmited == false )	{

					// rurl �� ����
					if( this.oBody.autoCreateRurl  )	{

					}

					this.isSubmited = true ;
					this.orgForm.submit() ;
					this.setTarget(this.preTarget) ;
					return true ;
				}
				else	{
					window.status = "Ȯ�ι�ư�� �ѹ��� �����ž� �մϴ�." ;
				}

			}
			else
				return false ;


		},


	// POPUP ���� SUBMIT 
	popSubmit :

		function()
		{
			if( this.validate() == true )	{

					this.orgForm.submit() ;
					this.setTarget(this.preTarget) ;
					return true ;

			}
			else
				return false ;


		}, 

		// override �ؼ� �߸�����Ʈ �� �� �ִ�.
		chk : 
			function() { return true ; },

		// �� ������ ����ó���� - �������̵� ó���� �� �ֽ��ϴ�.
		validate : 

			function(except)	{

			var f = this.orgForm	;
			var min = 0			;
			var necessary = "" ;
			var isExcept = false ;
			
			var chk_img = "";
			
			
			except = ( except == undefined ) ? new Array() : except ;

			for(var i = 0 ; i < f.elements.length ; i++ )	{

				for(var j = 0 ; j < except.length ; j++ )	{
					if( f.elements[i].name == except[j] )	{
						isExcept = true ;
						break ;
					}
				}
				if( isExcept == true )	{	
					isExcept = false ;
					continue ;
				}
				
				necessary = f.elements[i].getAttribute("VTYPE")	;
				min = f.elements[i].getAttribute("MIN_LENGTH")	;
				chk_img = f.elements[i].getAttribute("CHK_IMG")	;

				if( necessary != null )	{

					if( f.elements[i].type == "radio" )	{

						if( WPUtil.nullRadio(f[f.elements[i].name]) == false )	{
							try
							{
								f.elements[i].focus()	;
							}
							catch(e){ continue ; }
							alert(f.elements[i].title + "��(��) �ʼ� �Է� �����Դϴ�.") ;
							return false ;
						}
					}
					else if ( f.elements[i].type == "checkbox") {
							var sChkIndex = i ;
							var bOk = false ;
							while( f.elements[i].type == "checkbox" )	{
								if( f.elements[i].checked == true )	
									bOk = true ;
								i++ ;
							}

							if( bOk == false )	{
								alert(f.elements[sChkIndex].title + "��(��) �ʼ� �Է� �����Դϴ�.") ;
								return false ;
							}
				
					}
					else	
					{

						if( f.elements[i].value == "" )	{
							if( f.elements[i].disabled )
							{}
							else if( f.elements[i].type == "select-one" )
								f.elements[i].focus() ;
							else if(f.elements[i].readOnly == false )
								f.elements[i].focus() ;
						
							
							alert(f.elements[i].title + "��(��) �ʼ� �Է� �����Դϴ�.") ;
								
							return false ;
						}
					}
				}

				if( min != null ) {
					if( f.elements[i].value.length < min )	{
						alert(f.elements[i].title + "�� �ּ� " + min + "�� �̻� �Դϴ�. �ٽ� �Է��Ͽ� �ֽʽÿ�.") ;
						if( f.elements[i].readOnly == false )
							f.elements[i].focus() ;
						return false ;
					}
				}
				
				if(chk_img != null) {		
				
					var obj = f.elements[i]; 
				
					if(obj.value == '')
						return true;
			
					if(obj.value != '' && !obj.value.toLowerCase().match(/(.jpg|.jpeg|.gif|.png|.bmp)/)) { 
						alert('�̹��� ���ϸ� ���ε� �� �� �ֽ��ϴ�.');			
						obj.focus();
						//document.selection.clear();
						//document.execCommand('Delete');
						return false ;	
					}		
					
				}



			}
			
			// �������̵� �� chk() �Լ� ȣ��
			if( this.chk() == false )
				return false ; 

			return true ;
		},

	// �� Submit �� ���� �ൿ�� ��� �Ұ��� ���� action.xxx �� ����(�԰�) ���� �� ������Ʈ ���� V0.7
	//  nextType 
	//		- ToOpener
	//		- ToSelf
	//		- ToPrepageByRef
	//		- ToOpenerAndClose
	//		- OpenerRefreshAndClose
	//		- Close
	//		- ToPrepage
	nextActionCreate : 

		function(actionSC,nextType,nextUrl,nextMsg)
		{
				this.setMethod("POST")			;
				this.setAction(actionSC)		;
				this.create("rType",nextType)	;
				this.create("rUrl",nextUrl)		;
				this.create("rMsg",nextMsg)		;
		},

	// rurl �� ���� V0.7
	rurlCreate : 

		function(url)
		{
			this.ecreate("rurl",url) ;
		},


	// �����͸� ���ڵ��ؼ� ����
	ecreate : 

		function(name,value,type)
		{
			this.create(name,WPUtil.bin2hex(value),type) ;
		},


	// 2008.06.21  �����迭�� �������� ���� ��ȯ
	createAssoc :

		function(assoc)
		{
			for(key in assoc)
				if(assoc.hasOwnProperty(key)){
					this.create(key,assoc[key]) ;
				}
		},

	/* �ش� FORM �� INPUT �±� ����&���� */
	create : 

		function(name,value,type)
		{
				var obj ;
				var preObj ;
		
				preObj = document.getElementsByName(name)[0] ;
				
				if( preObj == undefined )	{
		
		
					var input = document.createElement("INPUT");
		
					input.type		= "hidden"	;
					input.name		= name		;
					input.id		= name		;
					input.value		= value		;
		
					type = ( type == undefined ) ? "afterBegin" : type ;

					obj = this.orgForm.appendChild(input);

				}
				else
				{
		
					preObj.value = value ;
					obj = preObj ;
				}
			
				return obj ;
		
		}

}) ;



Object.extend(RHForm.prototype,{

	nac : RHForm.prototype.nextActionCreate  

}) ;


// CMD ���� ������ �����Ʈ
Class.overload.apply(RHForm,[ 'submit',[ String ] , 
	function(cmd)
	{
			this.create("cmd",cmd) ;

			this.submit() ;
	} ]
) ;

// ���� ������ ������ �����Ʈ
Class.overload.apply(RHForm,[ 'submit',[ String , String , String , String ] , 
	function(cmd,nextType,nextUrl,nextMsg)
	{
			this.create("cmd",cmd) ;
			this.create("rType",nextType)	;
			this.ecreate("rUrl",nextUrl)	;
			this.create("rMsg",nextMsg)		;

			this.submit() ;
	} ]
) ;



