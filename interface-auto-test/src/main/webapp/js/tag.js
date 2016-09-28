/**
 * describe: 分会场标签创建
 * 
 * */
(function(w,$){
	var WS = function(opt){

		//var regexp = opt.regexp || /\S/,
		var el = $(opt.el), 
				list = el.val().split(';'),
				holder = $('<span class="words-split"></span>'),
				add = $('<a href="javascript:void(0)" class="words-split-add">+</a>');

		for (var i = 0; i < list.length; i++) {
			if(list[i]){
				holder.append( $('<a href="javascript:void(0)" class="fm-button">'+list[i]+'<em></em></a>') );
			}
			
		}

		if(el.parent().find(".words-split")){
			el.parent().find(".words-split").remove();
		}
		
		el.hide().after( holder );
		el.parent().find(".words-split-add").remove();
		holder.after(add);

		holder.on('click','a>em',function(){	// 删除
			$(this).parent().remove();
			el.val( holder.find("a").map(function() {
				  return $(this).text();
			}).get().join(';'));
		});
		
		add.on('click',function(){				//添加
			$(this).hide();
			holder.append( $('<span class="lbl-input" contenteditable="true"/>') );
		});
		
		holder.on('blur','.lbl-input',function(){	//验证添加字段
			var t = $(this),v = t.text();
			if(!v){
				t.remove();
				add.show();
			}else{
				t.remove();
				add.show();
				holder.append( $('<a href="javascript:void(0)" class="fm-button">'+v+'<em></em></a>') );
				el.val( holder.find("a").map(function() {
					  return $(this).text();
				}).get().join(';'));
			}
		});
		
		holder.on('keydown','.lbl-input',function(e){
			switch(e.keyCode){
				case 13:
				case 27: $(this).trigger('blur');
			}
		});
	};
	
	w.submeeting = WS;
		
})(window,jQuery);

// run
submeeting({el:'#submeeting'});



