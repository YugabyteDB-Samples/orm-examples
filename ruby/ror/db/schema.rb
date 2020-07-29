# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2019_05_04_204851) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "order_lines", force: :cascade do |t|
    t.string "orderId"
    t.integer "productId"
    t.integer "quantity"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "orders", force: :cascade do |t|
    t.string "orderId", null: false
    t.decimal "orderTotal"
    t.integer "userId"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["orderId"], name: "index_orders_on_orderId", unique: true
  end

  create_table "products", primary_key: "productId", force: :cascade do |t|
    t.string "productName"
    t.string "description"
    t.decimal "price"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "users", primary_key: "userId", force: :cascade do |t|
    t.string "firstName"
    t.string "lastName"
    t.string "email"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end
  add_foreign_key "orders", "users", column: "userId", primary_key: "userId"
end
