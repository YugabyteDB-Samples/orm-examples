class CreateOrders < ActiveRecord::Migration[5.2]
  def change
    create_table :orders , do |t|
      t.decimal :orderTotal
      t.integer :userId
      t.timestamps
    end
  end
end
