package com.routebox.routebox.exception.route

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.NotFoundException

class RouteNotFoundException : NotFoundException(CustomExceptionType.ROUTE_NOT_FOUND)
