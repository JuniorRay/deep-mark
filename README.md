# DeepMark一个基于网页的目标检测深度学习图片数据web标注工具，方便一个团队合作标注目标检测数据集。
不需要额外安装任何软件或者插件，只需要该电脑上有浏览器即可

.
深度学习图片标记工具项目启动步骤

0.启动路径为 MarkServerConfig.java,右键main 运行

1.使用前请传图片，默认上传位置在config-dev.txt

2.支持多选框功能任意切换,具体用法请参考demo

3.Utils.js中支持禁用开发者模式，方便加密、

4.支持多画笔颜色选择

5.返回点位接口已经预留在drawlltil.callback

6.支持：点，线，矩形，鼠标，梯形，三角形，多边形，圆形，箭头，平行四边等画笔工具

7.支持：框选放大，翻转，辅助线，旋转



//禁用浏览器右键，以及开发者模式
// Utils.banBrowserDefaultMouseEvents();

/**
 * Title: DrawingTools
 * Description: 基于canvas形状画图工具
 * @author JuniorRay
 * @date Oct 12, 2019
 *
 * 调用方式：

 var drawUtil=new DrawTools();
 
 //初始化，(如果浏览器不支持H5，会初始化失败，返回false)
 
 
 drawUtil.init({
 
        'canvasId':'cvs4',
        
        // "bgPic":"test.jpg",//有则设置背景,无则忽略
        
        // "paintConfig":{//初始化也可以传入样式
        
        //     lineWidth:2,//线条宽度，默认1
        
        //     strokeStyle:'blue',//画笔颜色，默认红色
        
        //     fillStyle:'red',//填充色
        
        //     lineJoin:"round",//线条交角样式，默认圆角
        
        //     lineCap:"round"//线条结束样式，默认圆角
        
        // }
        
    });
    
 //设置样式:
 
 // drawUtil.setStyle({
 
    //     lineWidth:2,//线条宽度，默认1
    
    //     strokeStyle:'blue',//画笔颜色，默认红色
    
    //     fillStyle:'red',//填充色
    
    //     lineJoin:"round",//线条交角样式，默认圆角
    
    //     lineCap:"round"//线条结束样式，默认圆角
    
    // });
    
 //加载图片
 
 drawUtil.setBgPic("test.jpg");
 

 // 鼠标=cursor，线条=line，三角形=triangle，矩形=rectangle，多边形=polygon，
 
 // 圆形=circle，箭头=arrows，平行四边形=parallelogram，梯形=trapezoid
 
 // drawUtil.begin('cursor');//选择画笔
 

 // drawUtil.begin('line');//选择画笔
 
 // drawUtil.begin('triangle');//选择画笔
 
 // drawUtil.begin('rectangle');//选择画笔
 
 drawUtil.begin('polygon');//选择画笔
 
 // drawUtil.begin('circle');//选择画笔
 
 // drawUtil.begin('arrows');//选择画笔
 
 // drawUtil.begin('parallelogram');//选择画笔
 
 // drawUtil.begin('trapezoid');//选择画笔
 

 drawUtil.callback({//框选结束后，底层会自动调用该函数
 
        end:function(e,r){
        
            console.log(e);
            
           alert(e.length) ;
           

            for(var i in e){
            
                console.log("x坐标："+e[i].getX()+"y坐标："+e[i].getY());
                
            }
            
             if(drawUtil.getDrawMode()=="circle"){
             
                console.log("radius:"+r);
                
            }
            
        }
        
    });
    
 //恢复鼠标手型，并停止绘图,返回绘制的坐标
 
 //  var points=drawUtil.end();
 
 //清除图形,停止绘制
 
 //  drawUtil.clear();
 

 //画网格线
 
 drawUtil.drawGrid(10,10);
 

 //关闭网格线
 
 drawUtil.closeGrid();
 
 //开启框选canvas放大功能
 
 drawUtil.openEnlarge();
 
 //关闭框选canvas放大功能
 
 drawUtil.closeEnlarge();,
 
 //恢复原有的框选点位的事件(从放大canvas功能切换成框选图片功能时用)
 
 drawUtil.recoverEvent();
 


 //获取当前画笔的模式（三角形，矩形等等,返回英文）
 
 drawUtil.getDrawMode();
 

 */
 

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







如有帮助到您，欢迎您的赞助~O(∩_∩)O
