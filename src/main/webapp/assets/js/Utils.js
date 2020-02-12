var Utils=function(){
    this.name='JuniorRay制作';

};

Utils.banBrowserDefaultMouseEvents=Utils.prototype.banBrowserDefaultMouseEvents=function () {
    //禁止浏览器默认鼠标事件
    document.oncontextmenu=function(){
        return false
    }
    /**禁用f12等开发者模式**/
    //禁用右键（防止右键查看源代码）
    window.οncοntextmenu=function(){
        return false;
    }
    //禁止任何键盘敲击事件（防止F12和shift+ctrl+i调起开发者工具）
    window.onkeydown = window.onkeyup = window.onkeypress = function () {
        window.event.returnValue = false;
        return false;
    };

    //判断浏览器开发者模式是否打开（console.log隐式调用元素的id）兼容浏览器：Chrome，IE11
    function isDevToolsOpened() {
        var opened = false;
        var element = document.createElement('any');
        element.__defineGetter__('id', function() {
            opened = true;
        });
        console.log(element);
        console.clear && console.clear();
        return opened || false;
    }
    //  轮询判断浏览器窗口是否打开，如果打开则强行关闭
    setInterval(function(){
        var isOpen=isDevToolsOpened();
        if(isOpen){
            window.location.reload();//强制刷新
            alert("已经禁用开发者模式,不允许打开开发者选项，请关闭！")
        }
    })
};


/**
 * Title: colorSelect
 * Description: 颜色选择器
 * @author JuniorRay
 * @date  2020-01-30
 * 使用方式：
 // 加载颜色选择器
 var colorSelect=Utils.colorSelect({
            id:'colorPane'
        });
 //打开颜色选择器
 // colorSelect.open();
 //关闭颜色选择器
 // colorSelect.close();
 //颜色选择结束时，获取颜色值
 colorSelect.callback({
      end:function(currentColor){
                alert("回调颜色："+currentColor);
      }
  });
 *
 * **/
//颜色选择器
Utils.colorSelect=Utils.prototype.colorSelect=function(obj){
    var domId=obj.id||alert("请传入元素id");

    var isSelectColor=false;//判断是否点击，获取颜色的标志位
    var ColorHex=new Array('00','33','66','99','CC','FF')
    var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
    var current=null;
    var currentDom=document.getElementById(domId);
    // currentDom.parentNode.style.position="relative"

    //获取currentDom相对浏览器的位置矩形
    var clientRect=currentDom.getBoundingClientRect();

    var colorPaneObj=document.createElement("div");
    // debugger
    colorPaneObj.style.position="fixed";
    colorPaneObj.style.top=clientRect.y+"px";
    colorPaneObj.style.left=clientRect.x+"px";
    colorPaneObj.style.zIndex="999";
    colorPaneObj.setAttribute('id','colorSelectPane')
    colorPaneObj.style.display="none";


    //在该元素后面追加节点
    function insertAfter(newElement, targetElement) {
        var parent = targetElement.parentNode;
        if (parent.lastChild == targetElement) {
            // 如果最后的节点是目标元素，则直接添加。因为默认是最后
            parent.appendChild(newElement);
        } else {
            parent.insertBefore(newElement, targetElement.nextSibling);
            //如果不是，则插入在目标元素的下一个兄弟节点 的前面。也就是目标元素的后面
        }
    }
    insertAfter(colorPaneObj,currentDom);
    var colorSelectPane=document.getElementById('colorSelectPane');
    var colorVal="";
    //获取选中的颜色
    getColor=function(){
        return colorVal;
    };
    //执行点击事件
    doClick=function(obj){
        // alert(obj);
        colorVal=obj;
        doClose();
        isSelectColor=true;//代表已经点击
        return obj;
    };
    //关闭颜色选择器
    doClose=function(){
        colorSelectPane.style.display = "none";
    };
    //打开颜色选择器
    doOpen=function(){
        colorSelectPane.style.display = "";
        //修复控件超出视野的情况
        fixOverView();
    };

    //修复控件超出视野的情况
    function fixOverView(){
        setTimeout(function () {
            //网页可见区域宽
            var pageWidth = document.body.clientWidth;
            //网页可见区域高
            var pageHeight =document.body.clientHeight;
            var toolsPosition=colorSelectPane.getBoundingClientRect();
            // colorSelectPane元素盒子左上角点位距离页面上边的距离
            var toolsTop=toolsPosition.top;
            // colorSelectPane元素盒子左上角点位距离页面右边的距离
            var toolsRight=toolsPosition.right;
            // colorSelectPane元素盒子左上角点位距离下边的距离
            var toolsBottom=toolsPosition.bottom;
            // colorSelectPane元素盒子左上角点位距离页面左边的距离
            var toolsLeft=toolsPosition.left;

            //如果组件右边超出
            if(toolsRight>pageWidth){
                colorSelectPane.style.right = 0 + "px";
                colorSelectPane.style.left = "auto";

            }
            //如果组件下边超出
            if(toolsBottom>pageHeight){
                colorSelectPane.style.bottom = 50 + "px";
                colorSelectPane.style.top = "auto";
            }
            // //如果组件左边超出
            // if(toolsLeft>pageWidth){
            //     colorSelectPane.style.right = 0 + "px";
            // }
            console.log("pageWidth:"+pageWidth);
            console.log("pageHeight:"+pageHeight);

            console.log("top:"+toolsTop); // 元素上边距离页面上边的距离

            console.log("right:"+toolsRight); // 元素右边距离页面左边的距离

            console.log("bottom:"+toolsBottom); // 元素下边距离页面上边的距离

            console.log("left:"+toolsLeft); // 元素左边距离页面左边的距离
        })
    }
    function initcolor(evt) {
        var colorTable=''
        for (i=0;i<2;i++)
        {
            for (j=0;j<6;j++)
            {
                colorTable=colorTable+'<tr height=15>'
                if (i==0){
                    colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'" onclick="doClick(this.style.backgroundColor)">'}
                else{
                    colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+SpColorHex[j]+'" onclick="doClick(this.style.backgroundColor)">'}
                for (k=0;k<3;k++)
                {
                    for (l=0;l<6;l++)
                    {
                        colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'" onclick="doClick(this.style.backgroundColor)">'
                    }
                }
            }
        }
        colorTable='<table border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse;width:305px;" bordercolor="000000">'
            +'<tr height=20><td colspan=21 bgcolor=#ffffff style="font:12px tahoma;padding-left:2px;">'
            +'<span style="float:left;color:#999999;">颜色托盘|点击选中颜色</span>'
            +'<span style="float:right;padding-right:3px;cursor:pointer;" onclick="doClose()">×关闭</span>'
            +'</td></table>'
            +'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" style="cursor:pointer;">'
            +colorTable+'</table>';

        colorSelectPane.innerHTML=colorTable;

        //修复控件超出视野的情况
        fixOverView();



    }



    //生成颜色选择器
   initcolor();


    return{
        open:doOpen,
        close:doClose,
        //  该回调函数颜色选择器界面关闭是，会实时获取颜色值
        callback:function(param){

            var timer=setInterval(function(){
                if(isSelectColor!=false){//判断运行鼠标结束后，直接返回颜色信息
                    //当前颜色值
                    var currentColor=getColor();
                    param.end(currentColor);
                    isSelectColor=false;//是否返回颜色的标志为复位
                    // clearInterval(timer);
                }

            })
        }

    }
};


