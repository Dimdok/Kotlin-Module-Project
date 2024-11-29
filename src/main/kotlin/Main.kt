fun main() {
    val app = NoteApp()
    app.start()
}

data class Archive(val name: String, val notes: MutableList<Note> = mutableListOf())
data class Note(val name: String, val content: String)
private class ExitException : RuntimeException()

private class NoteApp {
    private val archives = mutableListOf<Archive>()
    private val magicFactory = MagicFactory()

    fun start() {
        try {
            showRootMenu()
        } catch (_: ExitException) {
            println("Выход из программы.")
        }
    }

    private fun showRootMenu() {
        while (true) {
            val menuItems = mutableListOf(
                "Создать архив" to { createArchive() },
                "Выход из программы" to { throw ExitException() }
            )

            // добавляем существующие архивы в меню
            menuItems.addAll(archives.map { archive ->
                "Открыть архив \"${archive.name}\"" to { showArchiveMenu(archive) }
            })

            // генерация меню
            magicFactory.createAndNavigateMenu("Список архивов", menuItems)
        }
    }

    private fun createArchive() {
        val name = magicFactory.promptInput("Введите имя архива:")

        if (archives.any { it.name == name }) {
            println("Архив с именем \"$name\" уже существует. Попробуйте другое имя.")
            return
        }

        archives.add(Archive(name))
        println("Архив \"$name\" создан.")
    }

    private fun showArchiveMenu(archive: Archive) {
        while (true) {
            val menuItems = mutableListOf(
                "Создать заметку" to { createNote(archive) },
                "Назад" to { showRootMenu() }
            )

            menuItems.addAll(archive.notes.map { note ->
                "Открыть заметку \"${note.name}\"" to { println("\nСодержание заметки \"${note.name}\":\n${note.content}") }
            })

            magicFactory.createAndNavigateMenu("Содержание архива \"${archive.name}\": ", menuItems)
        }
    }

    private fun createNote(archive: Archive) {
        val name = magicFactory.promptInput("Введите название заметки:")

        if (archive.notes.any { it.name == name }) {
            println("Заметка с именем \"$name\" уже существует. Попробуйте другое имя.")
            return
        }

        val content = magicFactory.promptInput("Введите содержание заметки:")

        archive.notes.add(Note(name, content))
        println("Заметка \"$name\" создана.")
    }
}
