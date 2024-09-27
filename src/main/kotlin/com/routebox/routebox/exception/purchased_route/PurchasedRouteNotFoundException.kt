package com.routebox.routebox.exception.purchased_route

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.NotFoundException

class PurchasedRouteNotFoundException : NotFoundException(CustomExceptionType.PURCHASED_ROUTE_NOT_FOUND)
