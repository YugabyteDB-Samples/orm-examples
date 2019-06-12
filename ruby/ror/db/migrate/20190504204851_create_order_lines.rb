class CreateOrderLines < ActiveRecord::Migration[5.2]
  def change
    create_table :order_lines do |t|
      t.string :orderId
      t.integer :productId
      t.timestamps
    end
  end
end
