package com.abdelrahman.shoppingcart.enums;

public enum OrderStatus {
	PENDING, // الطلب اتعمل بس لسة محدش اشتغل عليه مش confirmed 
    PROCESSING,// هنا الطلب بيتجهز وبيتعمل خصم لل inventory , تجهيز المنتجات 
    SHIPPED,// هنا الطلب خرج من المخزن فعلا للشحن او ان شركةالشحن استلمت الطلب للعميل 
    DELIVERED,//خلاص الطلب وصل للعميل خلاص العملية انتهت بنجاح
    CANCELLED // الطلب اتلغى ممكن يكون مشكلة في الدفع مثلا
}
