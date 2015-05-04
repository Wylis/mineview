package mineview.core.storage

trait Storage {
  def add(x:AnyVal)
  def remove(x:AnyVal)
  def get(x:AnyVal)
}
