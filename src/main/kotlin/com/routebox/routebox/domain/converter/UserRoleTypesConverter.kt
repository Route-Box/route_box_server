package com.routebox.routebox.domain.converter

import com.routebox.routebox.domain.user.UserRoleType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.stream.Collectors

@Converter
class UserRoleTypesConverter : AttributeConverter<Set<UserRoleType>, String> {

    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: Set<UserRoleType>): String =
        attribute.stream()
            .map { roleType -> roleType.name }
            .sorted()
            .collect(Collectors.joining(DELIMITER))

    override fun convertToEntityAttribute(dbData: String): Set<UserRoleType> =
        dbData.split(DELIMITER)
            .map { roleType -> UserRoleType.valueOf(roleType) }
            .toSet()
}
