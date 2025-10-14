package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class DbMemberAddress {
    @Column(nullable = false, length = 3, name = "country_code")
    var countryCode: String? = null

    @Column(nullable = false)
    var state: String? = null

    @Column(nullable = false)
    var province: String? = null

    @Column(nullable = false)
    var city: String? = null

    @Column(nullable = true)
    var street: String? = null

    @Column(nullable = false, name = "building_number")
    var buildingNumber: String? = null

    @Column(nullable = true, name = "apartment_number")
    var apartmentNumber: String? = null

    @Column(nullable = false, name = "postal_code")
    var postalCode: String? = null
}