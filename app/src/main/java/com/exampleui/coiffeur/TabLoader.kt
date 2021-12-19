package com.exampleui.coiffeur

import com.google.android.material.tabs.TabLayout

object TabLoader {

    fun loadTabs(tabLayout: TabLayout, tabItems: List<String>) {
        tabItems.forEach { tabData ->
            tabLayout.newTab().also { tab ->
                tab.text = tabData
                tabLayout.addTab(tab)
            }
        }
    }
}