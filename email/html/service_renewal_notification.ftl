<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${serviceName}即将自动续订</title>
</head>
<body style="font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 20px; background-color: #f5f5f5;">
    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
        <!-- 头部区域 -->
        <div style="background: linear-gradient(135deg, #00b894 0%, #00cec9 100%); color: white; padding: 30px; border-radius: 8px 8px 0 0; text-align: center;">
            <h1 style="margin: 0; font-size: 24px; font-weight: bold;">${serviceName}即将自动续订</h1>
        </div>
        
        <!-- 内容区域 -->
        <div style="padding: 30px;">
            <!-- 称呼 -->
            <div style="margin-bottom: 25px;">
                <p style="margin: 0 0 10px 0; font-size: 16px;">亲爱的绿联用户</p>
                <p style="margin: 0; font-size: 16px;">您好！</p>
            </div>
            
            <!-- 主要内容 -->
            <div style="background-color: #f0fff4; border: 1px solid #00b894; border-radius: 6px; padding: 20px; margin-bottom: 25px;">
                <p style="margin: 0; font-size: 16px;">
                    您的当前订阅（<#if deviceName??><strong style="color: #2d3436;">${deviceName}</strong>设备的</#if><strong style="color: #2d3436;">${serviceName}</strong>）将于
                    <span style="color: #00b894; font-weight: bold; font-size: 18px;">${expireTime}</span>自动续期。
                </p>
            </div>
            
            <!-- 继续使用说明 -->
            <div style="margin-bottom: 25px;">
                <h3 style="color: #2d3436; margin: 0 0 15px 0; font-size: 18px;">
                    <span style="color: #00b894; margin-right: 8px;">▶</span>继续使用——
                </h3>
                <div style="background-color: #f8f9fa; border-radius: 6px; padding: 15px; margin-left: 20px;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        无需操作，您将继续使用<strong>${serviceName}</strong>。
                    </p>
                </div>
            </div>
            
            <!-- 取消自动续费说明 -->
            <div style="margin-bottom: 25px;">
                <h3 style="color: #2d3436; margin: 0 0 15px 0; font-size: 18px;">
                    <span style="color: #fdcb6e; margin-right: 8px;">▶</span>取消自动续费——
                </h3>
                <div style="background-color: #fffbf0; border: 1px solid #fdcb6e; border-radius: 6px; padding: 15px; margin-left: 20px;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        请务必在<span style="color: #e74c3c; font-weight: bold;"> ${expireTime} </span>前取消续订。
                    </p>
                </div>
            </div>
            
            <!-- 操作步骤 -->
            <div style="margin-bottom: 30px;">
                <h3 style="color: #2d3436; margin: 0 0 15px 0; font-size: 18px;">
                    <span style="color: #74b9ff; margin-right: 8px;">▶</span>取消步骤：
                </h3>
                <div style="background-color: #f0f8ff; border-radius: 6px; padding: 15px; margin-left: 20px;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        登录UgreenCare App，进入「我的」>「订单管理」>「订阅管理」中进行操作。
                    </p>
                </div>
            </div>
            
            <!-- 特别提醒 -->
            <div style="margin-bottom: 30px;">
                <div style="background-color: #fff5f5; border-left: 4px solid #e74c3c; padding: 15px; border-radius: 0 6px 6px 0;">
                    <p style="margin: 0; font-size: 15px; color: #555;">
                        <strong style="color: #e74c3c;">温馨提醒：</strong>
                        如果您在扣费日期前没有取消自动续订，系统将自动从您的账户中扣费并延长服务期限。
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