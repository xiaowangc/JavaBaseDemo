<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>[${serviceName}] 即将到期，请及时续订！</title>
</head>
<body style="font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 20px; background-color: #f5f5f5;">
    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
        <!-- 头部区域 -->
        <div style="background: linear-gradient(135deg, #e17055 0%, #fd7e14 100%); color: white; padding: 30px; border-radius: 8px 8px 0 0; text-align: center;">
            <h1 style="margin: 0; font-size: 24px; font-weight: bold;">
                [${serviceName}] 即将到期，请及时续订！
            </h1>
        </div>
        
        <!-- 内容区域 -->
        <div style="padding: 30px;">
            <!-- 称呼 -->
            <div style="margin-bottom: 25px;">
                <p style="margin: 0 0 10px 0; font-size: 16px;">亲爱的绿联用户</p>
                <p style="margin: 0; font-size: 16px;">您好！</p>
            </div>
            
            <!-- 主要内容 -->
            <div style="background-color: #fff8f0; border: 1px solid #ffeaa7; border-radius: 6px; padding: 20px; margin-bottom: 25px;">
                <p style="margin: 0; font-size: 16px;">
                    <#if deviceName??>
                        您订阅的<strong style="color: #2d3436;">${deviceName}</strong>设备的<strong style="color: #2d3436;">${serviceName}</strong>将于
                    <#else>
                        您的<strong style="color: #2d3436;">${serviceName}</strong>将于
                    </#if>
                    <span style="color: #e74c3c; font-weight: bold; font-size: 18px;">${expireTime}</span>到期。
                    为了确保服务不中断，请及时续订。
                </p>
            </div>
            
            <!-- 续订步骤 -->
            <div style="margin-bottom: 25px;">
                <h3 style="color: #2d3436; margin: 0 0 15px 0; font-size: 18px;">
                    <span style="color: #00b894; margin-right: 8px;">▶</span>续订步骤：
                </h3>
                <div style="background-color: #f8f9fa; border-radius: 6px; padding: 15px; margin-left: 20px;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        登录UgreenCare App，进入「我的」>「订单管理」>「订阅管理」中进行续订。
                    </p>
                </div>
            </div>
            
            <!-- 重要提醒 -->
            <div style="margin-bottom: 30px;">
                <h3 style="color: #2d3436; margin: 0 0 15px 0; font-size: 18px;">
                    <span style="color: #e17055; margin-right: 8px;">▶</span>重要提醒：
                </h3>
                <div style="background-color: #fff5f5; border-left: 4px solid #e74c3c; padding: 15px; margin-left: 20px; border-radius: 0 6px 6px 0;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        若您不再使用此服务，在服务到期后，系统将为您保留文件7天，之后将清空所有数据且不可恢复。请及时处理重要文件，避免丢失！
                    </p>
                </div>
            </div>
        </div>
        
        <!-- 底部区域 -->
        <div style="background-color: #f8f9fa; padding: 20px 30px; border-radius: 0 0 8px 8px; border-top: 1px solid #dee2e6;">
            <p style="margin: 0 0 5px 0; color: #666; font-size: 14px;">感谢您的使用！</p>
            <p style="margin: 0; color: #666; font-size: 14px; font-weight: bold;">绿联团队</p>
            <p style="margin: 15px 0 0 0; color: #999; font-size: 12px;">
                发送时间：${sendTime}
            </p>
        </div>
    </div>
</body>
</html> 