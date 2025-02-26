sealed trait Token
case class Identifier(value: String) extends Token
case class Integer(value: Int) extends Token
case object LParen extends Token
case object RParen extends Token
// ...
