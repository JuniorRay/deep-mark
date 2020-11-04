(function(window){
    "use strict";//"use strict" 的目的是指定代码在严格条件下执行。严格模式下你不能使用未声明的变量。

    layui.use(['element','form','layer','upload'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var upload=layui.upload;
        form.render();
        // 加载颜色选择器
        var colorSelect=Utils.colorSelect({
            id:'colorPane'
        });
        //打开颜色选择器
        // colorSelect.open();
        //关闭颜色选择器
        // colorSelect.close();




        $("#colorPane").click(function(){
            //打开颜色选择器
            colorSelect.open();
        });



        //初始化画笔颜色
        var tempStrokeStyle='blue';
        //初始化画笔宽度
        var tempLineWidth=1;
        //初始化图片路径
        // var imgPath="/assets/img/test/testHuman.jpg";
        var imgPath="/assets/img/test/test.png";


        //加载画笔工具
        var drawUtil=new DrawTools();
        // 初始化canvas的宽高
        window.onresize=function(){
            resizeCanvas();
            drawUtil.setBgPic(imgPath);
        };
        resizeCanvas();
        function resizeCanvas(){
            var width= $("#canvas-box").width();
            var height=$("#canvas-box").height();
            $("#canvasId").attr({
                width:width,
                height:height
            })

        }


        //初始化，(如果浏览器不支持H5，会初始化失败，返回false)
        drawUtil.init({
            'canvasId':'canvasId',
            "bgPic":imgPath,//有则设置背景,无则忽略
            // "paintConfig":{//初始化也可以传入样式
            //     lineWidth:2,//线条宽度，默认1
            //     strokeStyle:'blue',//画笔颜色，默认红色
            //     fillStyle:'red',//填充色
            //     lineJoin:"round",//线条交角样式，默认圆角
            //     lineCap:"round"//线条结束样式，默认圆角
            // }，
            // banBrowserDefaultMouseEvents:true//禁用浏览器右键
        });
        window.drawUtil=drawUtil;//赋值给全局对象



        //设置样式:
        drawUtil.setStyle({
            lineWidth:tempLineWidth,//线条宽度，默认1
            strokeStyle:tempStrokeStyle,//画笔颜色，默认红色
            fillStyle:'red',//填充色
            lineJoin:"round",//线条交角样式，默认圆角
            lineCap:"round"//线条结束样式，默认圆角
        });
        $("#colorPane").css({background:tempStrokeStyle});
        //颜色选择器结束时，获取颜色值
        colorSelect.callback({
            end:function(currentColor){
                // alert("回调颜色："+currentColor);
                $("#colorPane").css({background:currentColor});
                tempStrokeStyle=currentColor;
                //设置样式:
                drawUtil.setStyle({
                    strokeStyle:tempStrokeStyle,//画笔颜色，默认红色
                    lineWidth:tempLineWidth,//线条宽度，默认1

                });
            }
        });
        // 实时监听画笔大小变化进行设置参数
        $('#lineWidth').bind('input propertychange', function() {
            var lineWidth=$("#lineWidth").val();
            var maxLineWidth=10;
            var minLineWidth=1;
            if(lineWidth<minLineWidth){
                $("#lineWidth").val(minLineWidth);
                layer.tips('画笔宽度不能小于'+minLineWidth, $(this), {
                    tips: [1, '#3d7af5'],
                });
            }
            if(lineWidth>maxLineWidth){
                $("#lineWidth").val(maxLineWidth);

                layer.tips('画笔宽度不能大于'+maxLineWidth, $(this), {
                    tips: [1, '#3d7af5'],
                });
            }
            tempLineWidth=lineWidth;
            //设置样式:
            drawUtil.setStyle({
                strokeStyle:tempStrokeStyle,//画笔颜色，默认红色
                lineWidth:tempLineWidth,//线条宽度，默认1
            });
        });

        //加载图片
        // drawUtil.setBgPic("/assets/img/test/test.jpg");

        // 鼠标=cursor，线条=line，三角形=triangle，矩形=rectangle，多边形=polygon，
        // 圆形=circle，箭头=arrows，平行四边形=parallelogram，梯形=trapezoid
        // drawUtil.begin('cursor');//选择画笔
        drawUtil.begin('rectangle');//选择画笔,默认框选

        // drawUtil.begin('line');//选择画笔
        // drawUtil.begin('triangle');//选择画笔
        // drawUtil.begin('rectangle');//选择画笔
        // drawUtil.begin('polygon');//选择画笔
        // drawUtil.begin('circle');//选择画笔
        // drawUtil.begin('arrows');//选择画笔
        // drawUtil.begin('parallelogram');//选择画笔
        // drawUtil.begin('trapezoid');//选择画笔
        $(".toolModeGroup").find("input").each(function(){
            var mode=$(this). attr('mode');
            // console.log(mode);

            form.on('radio('+mode+')', function(data){
                // console.log(data);
                //恢复原有的框选点位的事件(从放大canvas功能切换成框选图片功能时用)
                drawUtil.recoverEvent();
                //切换画笔模式
                drawUtil.begin(mode);//选择画笔


            });

        });
        //	物体类型选择
        $(".typeGroup").find("input").each(function(){
            var mode=$(this). attr('mode');
            // console.log(mode);

            form.on('radio('+mode+')', function(data){
                // console.log(data);


            });

        });


        //选择结束后，底层会自动调用该函数
        drawUtil.callback({//框选结束后，底层会自动调用该函数，目前只支持多边形，明天我有空再改改
            end:function(e,r,realPoint){
                // console.log(e);
                // alert(e.length) ;

                for(var i in e){
                    console.log("x坐标："+e[i].getX()+"  y坐标："+e[i].getY());
                }
                for(var i in realPoint){
                    console.log("x坐标(real)："+realPoint[i].getX()+"  y坐标(real)："+realPoint[i].getY());
                }

                if(drawUtil.getDrawMode()=="circle"){
                    console.log("radius:"+r);
                }
            }
        });


        /*恢复鼠标手型，并停止绘图,返回绘制的坐标*/
        //  var points=drawUtil.end();
        //清除图形,停止绘制
        //  drawUtil.clear();
        //禁用浏览器右键
        //     drawUtil.banBrowserDefaultMouseEvents(true);

        //	创建上传列表
        function createUploadList(path,index){//index上传顺序的索引
            var listItems='<li class="image-item" title="'+path+'" ' +
                'data-path="/res?path='+path+
                '" data-index="'+index+'" >' +
                path+
                '</li>';
            return listItems;
        }

        //动态监听生成的li并添加点击事件，给来替换canvas
        $(".image-list").on("click",'li',function () {
            // debugger
            var path=$(this).data("path");
            //进入选中状态
            $(this).addClass("selected");
            $(this).siblings().removeClass("selected")
            //canvas加载图片
            drawUtil.setBgPic(path);


        });
        var gridOpenFlag=false;

        //网格辅助线点击事件
        $("#grid-line").click(function () {
            if(!gridOpenFlag){//第一次进来画表格
                $(this).addClass("softSz-btn-primary")
                drawUtil.drawGrid(10,10);
                gridOpenFlag=true;

            }else{//第二次进来隐藏表格
                $(this).removeClass("softSz-btn-primary")

                drawUtil.closeGrid();
                gridOpenFlag=false;

            }

            // if($(".gridCell").hasClass("hidden")){
            //     $(".gridCell").removeClass("hidden");
            // }else{
            //     $(".gridCell").addClass("hidden");
            // }

        });

        //批量上传图片
        upload.render({
            elem: '#importBtn'
            ,url: '/res/upload/'
            ,multiple: true
            ,choose: function(obj){
                //将每次选择的文件追加到文件队列
                var files = obj.pushFile();

                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function(index, file, result) {
                    // console.log(index); //得到文件索引
                    // console.log(file); //得到文件对象
                    var li=createUploadList(file.name,index);
                    $(".image-list").append(li);
                    // console.log(result); //得到文件base64编码，比如图片

                })

            }
            ,allDone: function(obj){ //当文件全部被提交后，才触发
                // console.log(obj.total); //得到总文件数
                // console.log(obj.successful); //请求成功的文件数
                // console.log(obj.aborted); //请求失败的文件数
                $(".image-item:first").addClass("selected");//默认第一个选中
                var path=$(".image-item:first").data("path");
                drawUtil.setBgPic(path);

            }
            ,done: function(res, index, upload){ //每个文件提交一次触发一次。详见“请求成功的回调”
                console.log(res); //res（服务端响应信息）

                console.log(index); //index（当前文件的索引）
                console.log(upload);//upload（重新上传的方法，一般在文件上传失败后使用）
                //改变原有li中的data-path的,为真实服务器保存的路径
                // debugger
                var currentLi=$(".image-item[data-index='"+index+"']");
                //res.path为服务器真实存储的路径
                $(currentLi).attr("data-path",'/res?path='+res.path);
            }
        });
        //删除当前照片
        $("#deleteBtn").click(function () {
            var path=$(".image-item.selected").data("path");
            path=path.replace('/res?path=','');
            $.ajax({
                type: "post",
                url: "/res/remove",
                data: {
                    path:path,
                },
                dataType: "json",
                success: function(data){
                    if(data.state=="ok"){
                        $(".image-item.selected").remove();
                        //如果列表中还存在，自动切换到下一个
                        if($(".image-item:first").length>0){
                            $(".image-item:first").addClass("selected");//默认第一个选中
                            var path=$(".image-item:first").data("path");
                            drawUtil.setBgPic(path);
                        }else{//没有则清空
                            drawUtil.setBgPic(imgPath);
                        }


                    }else{
                        layer.msg("删除失败");
                    }

                },error:function(data){
                    layer.msg("删除失败");
                }
            });

        });

        //图片翻转点击事件
        $(".rollingOver").find("input").each(function(){
            var rotateStyle=$(this).attr("mode");

            $(this).click(function(){
                var path=$(".image-item.selected").data("path")||'';
                path=path.replace('/res?path=','');
                if(path==null||path==""){
                    var msg="请先上传图片";
                    layer.msg(msg);
                    layer.tips(msg, $("#importBtn"), {
                        tips: [1, '#3d7af5'],
                    });
                    return
                };
                if(rotateStyle=="rollingOverLeft"){//左右翻转
                    rollingOver(path,1);
                }else if(rotateStyle=="rollingOverUp"){//上下翻转
                    rollingOver(path,0);
                }
            });

        });

        /**var angleArr=[90,180,270,360];
         var angleArrIndex=0;
         //图片旋转点击事件
         $(".rotateGroup").find("input").each(function(){
				var rotateStyle=$(this).attr("mode");

				$(this).click(function(){
					var path=$(".image-item.selected").data("path")||"";
					path=path.replace('/res?path=','');

					//重置防止越界
					if(angleArrIndex==angleArr.length){
						angleArrIndex=0;
					}
					//重置防止越界
					if(angleArrIndex==-1){
						angleArrIndex=angleArr.length-1;
					}
					var  angle=angleArr[angleArrIndex];

					if(rotateStyle=="rightRotate"){
						console.log("angle=="+angle+":"+angleArrIndex);

						rotate(path,angle);
						angleArrIndex++;

					}else if(rotateStyle=="leftRotate"){
						console.log("angle=="+angle+":"+angleArrIndex);
						rotate(path,angle);
						angleArrIndex--;
					}

				});

			});**/
        //图片旋转点击事件
        $(".rotateGroup").find("input").each(function(){
            var rotateStyle=$(this).attr("mode");

            $(this).click(function(){
                var path=$(".image-item.selected").data("path")||"";
                path=path.replace('/res?path=','');
                if(path==null||path==""){
                    var msg="请先上传图片";
                    layer.msg(msg);
                    layer.tips(msg, $("#importBtn"), {
                        tips: [1, '#3d7af5'],
                    });
                    return
                };

                if(rotateStyle=="rightRotate"){


                    rotate(path,90);

                }else if(rotateStyle=="leftRotate"){

                    rotate(path,270);

                }

            });

        });


        //旋转
        function rotate(path,angle){


            $.ajax({
                type: "post",
                url: "/rotate/rotateImage",
                data: {
                    path:path,
                    angle:angle
                },
                dataType: "json",
                success: function(data){
                    if(data.state=="ok"){
                        //path：2020/2/5fd2bfeea-f9c0-42bf-a090-2124c301d8b0_90.jpg
                        var realPath='/res?path='+(data.path);
                        //设置canvas背景
                        drawUtil.setBgPic(realPath);
                        //替换原有的保存路径
                        $(".image-item.selected").data("path",realPath);

                    }else{
                        layer.msg("失败");
                    }

                },error:function(data){
                    layer.msg("失败");
                }
            });
        }

        //翻转
        function rollingOver(path,rotateFlag){//rotateFlag 0 为上下翻转，1 为左右翻转

            $.ajax({
                type: "post",
                url: "/rotate/rollingOver",
                data: {
                    path:path,
                    //rotateFlag 0 为上下翻转，1 为左右翻转
                    rotateFlag:rotateFlag

                },
                dataType: "json",
                success: function(data){
                    if(data.state=="ok"){
                        var realPath='/res?path='+(data.path);
                        //设置canvas背景
                        drawUtil.setBgPic(realPath);
                        //替换原有的保存路径
                        $(".image-item.selected").data("path",realPath);

                    }else{
                        layer.msg("失败");
                    }

                },error:function(data){
                    layer.msg("失败");
                }
            });
        }


        //放大
        $(".enlarge").click(function(){
            //选择画笔，恢复手型
            $("input[mode='cursor']+.layui-form-radio").click();
            //开启canvas放大
            drawUtil.openEnlarge();
        });

        // 重置放大效果
        $(".resetView").click(function(){
            //选择画笔，恢复手型
            $("input[mode='cursor']+.layui-form-radio").click();
            //关闭canvas放大
            drawUtil.closeEnlarge();

        })


    });
})(window);