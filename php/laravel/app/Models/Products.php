<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Products extends Model
{
    public $timestamps = false;

    // protected $primaryKey = 'product_id';
    
    use HasFactory;

    protected $fillable = [
        'productName',
        'description',
        'price',
    ];
}
