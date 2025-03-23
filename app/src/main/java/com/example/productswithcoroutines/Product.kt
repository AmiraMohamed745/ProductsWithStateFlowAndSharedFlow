package com.example.productswithcoroutines

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*
@Entity(tableName = Constants.PRODUCT_TABLE)
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val brand: String?,
    val price: Double,
    val thumbnail: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(), // brand
        parcel.readDouble(),
        parcel.readString()!!,
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(brand)
        parcel.writeDouble(price)
        parcel.writeString(thumbnail)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}*/

@Entity(tableName = Constants.PRODUCT_TABLE)
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val brand: String?,
    val price: Double,
    val thumbnail: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString() ?: "",
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(brand)
        parcel.writeDouble(price)
        parcel.writeString(thumbnail)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

/*@Entity(tableName = Constants.PRODUCT_TABLE)
@Parcelize
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val brand: String?,
    val price: Double,
    val thumbnail: String
) : Parcelable */

