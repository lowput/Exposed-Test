import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object User : Table("user") {
    val id = integer("user_id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val age = integer("age")
}

fun main(args: Array<String>) {
    Database.connect("jdbc:mysql://localhost:3306/test",
            "com.mysql.jdbc.Driver",
            "user_name",
            "password" )

    transaction {
        logger.addLogger(StdOutSqlLogger)
        SchemaUtils.create(User)

        User.insert {
            it[name] = "foo"
            it[age] = 20
        }

        User.update({User.id eq 1}) {
            it[name] = "bar"
            it[age] = 30
        }
    }
}
