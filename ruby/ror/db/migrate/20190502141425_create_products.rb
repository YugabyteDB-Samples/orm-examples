class CreateProducts < ActiveRecord::Migration[5.2]
  def change
    create_table :products do |t|
      t.string :productName
      t.string :description
      t.decimal :price

      t.timestamps
    end
  end
end
