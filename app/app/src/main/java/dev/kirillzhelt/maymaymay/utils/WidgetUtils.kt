package dev.kirillzhelt.maymaymay.utils

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.findCheckedChipTexts() = this.checkedChipIds.map { chipId ->
        this.findViewById<Chip>(chipId).text.toString()
    }
