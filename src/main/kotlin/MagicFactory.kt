class MagicFactory {
    fun createAndNavigateMenu(menuTitle: String, menuItems: List<Pair<String, () -> Unit>>) {
        while (true) {
            println("\n$menuTitle")
            menuItems.forEachIndexed { index, (title, _) -> println("$index. $title") }
            print("Выберите пункт меню: ")

            val choice = readlnOrNull()?.toIntOrNull()

            if (choice == null || choice !in menuItems.indices) {
                println("Некорректный ввод. Попробуйте снова.")
                continue
            }
            // выполнение действия
            menuItems[choice].second()
            // после этого возвращаемся в меню верхнего уровня
            return
        }
    }

    fun promptInput(prompt: String): String {
        while (true) {
            println(prompt)
            val input = readlnOrNull().orEmpty()

            if (input.isNotEmpty()) {
                return input
            }

            println("Некорректный ввод. Попробуйте снова.")
        }
    }
}
