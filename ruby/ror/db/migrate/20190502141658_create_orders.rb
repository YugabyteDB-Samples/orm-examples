class CreateOrders < ActiveRecord::Migration[5.2]
  def change
    create_table :orders do |t|
      t.string :orderId,  null: false
      t.decimal :orderTotal
      t.integer :userId
      t.timestamps
    end
    add_index :orders, :orderId, unique: true
  end
end
