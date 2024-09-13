package com.routebox.routebox.exception.purchased_route

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ForbiddenException

class RouteNotPurchasedException : ForbiddenException(CustomExceptionType.ROUTE_NOT_PURCHASED)
