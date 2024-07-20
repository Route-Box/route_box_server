package com.routebox.routebox.config

import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext

@MockBean(JpaMetamodelMappingContext::class)
@Import(TestSecurityConfig::class)
class ControllerTestConfig
