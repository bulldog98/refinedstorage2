import os
import json
import shutil

output_dir = '../src/generated/resources/'

shutil.rmtree(output_dir)


def create_file(path, contents):
    print('Generating ' + path)
    try:
        os.makedirs(os.path.dirname(path))
    except:
        pass
    with open(path, 'w') as file:
        file.write(contents)


def to_json(data):
    return json.dumps(data, indent=2)


def get_color_key(color, name, default_color = 'light_blue'):
    if color == default_color:
        return name
    return color + '_' + name


def generate_controller_block_model(color):
    create_file(output_dir + '/assets/refinedstorage2/models/block/controller/' + color + '.json', to_json({
        'parent': 'refinedstorage2:block/emissive_all_cutout',
        'textures': {
            'particle': 'refinedstorage2:block/controller/off',
            'all': 'refinedstorage2:block/controller/on',
            'cutout': 'refinedstorage2:block/controller/cutouts/' + color
        }
    }))


def generate_controller_blockstate(name, color):
        create_file(output_dir + '/assets/refinedstorage2/blockstates/' + name + '.json', to_json({
          'variants': {
            'energy_type=off': {
              'model': 'refinedstorage2:block/controller/off'
            },
            'energy_type=nearly_off': {
              'model': 'refinedstorage2:block/controller/nearly_off'
            },
            'energy_type=nearly_on': {
              'model': 'refinedstorage2:block/controller/nearly_on'
            },
            'energy_type=on': {
              'model': 'refinedstorage2:block/controller/' + color
            }
          }
        }))


def generate_controller_item(name, color):
        create_file(output_dir + '/assets/refinedstorage2/models/item/' + name + '.json', to_json({
          'parent': 'item/generated',
          'overrides': [
            {
              'predicate': {
                'refinedstorage2:stored_in_controller': 0,
              },
              'model': 'refinedstorage2:block/controller/off'
            },
            {
              'predicate': {
                'refinedstorage2:stored_in_controller': 0.01,
              },
              'model': 'refinedstorage2:block/controller/nearly_off'
            },
            {
              'predicate': {
                'refinedstorage2:stored_in_controller': 0.3,
              },
              'model': 'refinedstorage2:block/controller/nearly_on'
            },
            {
              'predicate': {
                'refinedstorage2:stored_in_controller': 0.4,
              },
              'model': 'refinedstorage2:block/controller/' + color
            }
          ]
        }))


def generate_creative_controller_item(name, color):
        create_file(output_dir + '/assets/refinedstorage2/models/item/' + name + '.json', to_json({
          'parent': 'refinedstorage2:block/controller/' + color
        }))


def generate_cable_item(name, color):
    result = {
        "parent": "refinedstorage2:item/cable/base",
        "textures": {
            "cable": "refinedstorage2:block/cable/" + color
        }
    }

    create_file(output_dir + '/assets/refinedstorage2/models/item/' + name + '.json', to_json(result))


def generate_north_cutout_block_model(name, particle, north, east, south, west, up, down, cutout, emissive_cutout):
    parent = 'refinedstorage2:block/emissive_north_cutout' if emissive_cutout else 'refinedstorage2:block/north_cutout'

    create_file(output_dir + '/assets/refinedstorage2/models/block/' + name + '.json', to_json({
        'parent': parent,
        'textures': {
            'particle': particle,
            'north': north,
            'east': east,
            'south': south,
            'west': west,
            'up': up,
            'down': down,
            'cutout': cutout
        }
    }))


def generate_referencing_item_model(name, reference):
    create_file(output_dir + '/assets/refinedstorage2/models/item/' + name + '.json', to_json({
        'parent': reference
    }))


bi_direction_rotations = {
    'up_north': {
        'x': -90,
        'y': 180
    },
    'up_east': {
        'x': -90,
        'y': -90
    },
    'up_south': {
        'x': -90,
        'y': 0
    },
    'up_west': {
        'x': -90,
        'y': 90
    },
    'down_north': {
        'x': 90
    },
    'down_east': {
        'x': 90,
        'y': 90
    },
    'down_south': {
        'x': 90,
        'y': 180
    },
    'down_west': {
        'x': 90,
        'y': -90
    },
    'north': {},
    'east': {
        'y': 90
    },
    'south': {
        'y': 180
    },
    'west': {
        'y': 270
    }
}


def generate_blockstate_for_each_bi_direction_and_active(name, model_factory):
    result = {
        'variants': {}
    }

    for direction in bi_direction_rotations.keys():
        for active in [True, False]:
            result['variants']['direction=' + direction + ',active=' + str(active).lower()] = {
                'model': model_factory(direction, active),
                'x': bi_direction_rotations[direction].get('x', 0),
                'y': bi_direction_rotations[direction].get('y', 0)
            }

    create_file(output_dir + '/assets/refinedstorage2/blockstates/' +
                name + '.json', to_json(result))


def generate_cable_blockstate(name, color):
    result = {
        'multipart': [
            {
                'apply': {
                    'model': 'refinedstorage2:block/cable/core/' + color
                }
            },
            {
                'when': {
                    'north': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color
                }
            },
            {
                'when': {
                    'east': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color,
                    'y': 90
                }
            },
            {
                'when': {
                    'south': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color,
                    'x': 180
                }
            },
            {
                'when': {
                    'west': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color,
                    'y': 270
                }
            },
            {
                'when': {
                    'up': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color,
                    'x': 270
                }
            },
            {
                'when': {
                    'down': True
                },
                'apply': {
                    'model': 'refinedstorage2:block/cable/extension/' + color,
                    'x': 90
                }
            },
        ]
    }

    create_file(output_dir + '/assets/refinedstorage2/blockstates/' +
                name + '.json', to_json(result))


def generate_cable_models(color):
    result = {
        "parent": "refinedstorage2:block/cable/core/base",
        "textures": {
            "cable": "refinedstorage2:block/cable/" + color,
            "particle": "refinedstorage2:block/cable/" + color,
        },
    }

    create_file(output_dir + '/assets/refinedstorage2/models/block/cable/core/' +
                color + '.json', to_json(result))

    result = {
        "parent": "refinedstorage2:block/cable/extension/base",
        "textures": {
            "cable": "refinedstorage2:block/cable/" + color,
            "particle": "refinedstorage2:block/cable/" + color,
        },
    }

    create_file(output_dir + '/assets/refinedstorage2/models/block/cable/extension/' +
                color + '.json', to_json(result))


def generate_recipe(name, data):
    create_file(output_dir + '/data/refinedstorage2/recipes/' +
                name + '.json', to_json(data))


def generate_item_tag(name, data):
    create_file(output_dir + '/data/refinedstorage2/tags/items/' +
                name + '.json', to_json(data))


def generate_simple_loot_table(name, block, functions=[]):
    create_file(output_dir + '/data/refinedstorage2/loot_tables/blocks/' + name + '.json', to_json({
        'type': 'minecraft:block',
        'pools': [
            {
                'rolls': 1,
                'entries': [
                    {
                        'type': 'minecraft:item',
                        'functions': functions,
                        'name': block
                    }
                ],
                'conditions': [
                    {
                        'condition': 'minecraft:survives_explosion'
                    }
                ]
            }
        ]
    }))


with open('colors.txt') as colors_file:
    color_entries = colors_file.read().splitlines()
    color_names = list(map(lambda color: color.split(';')[0], color_entries))

    for color_entry in color_entries:
        color = color_entry.split(';')[0]
        dye = color_entry.split(';')[1]

        generate_controller_block_model(color)
        generate_controller_blockstate(get_color_key(color, 'controller'), color)
        generate_controller_blockstate(get_color_key(color, 'creative_controller'), color)
        generate_controller_item(get_color_key(color, 'controller'), color)
        generate_creative_controller_item(get_color_key(color, 'creative_controller'), color)

        generate_north_cutout_block_model('grid/' + color, particle='refinedstorage2:block/grid/right', east='refinedstorage2:block/grid/right', south='refinedstorage2:block/grid/back', west='refinedstorage2:block/grid/left',
                                          up='refinedstorage2:block/grid/top', down='refinedstorage2:block/bottom', north='refinedstorage2:block/grid/front', cutout='refinedstorage2:block/grid/cutouts/' + color, emissive_cutout=True)
        generate_referencing_item_model(
            get_color_key(color, 'grid'), 'refinedstorage2:block/grid/' + color)
        generate_blockstate_for_each_bi_direction_and_active(get_color_key(
            color, 'grid'), lambda direction, active: 'refinedstorage2:block/grid/' + color if active else 'refinedstorage2:block/grid/inactive')

        generate_cable_item(get_color_key(color, 'cable', 'gray'), color)
        generate_cable_blockstate(get_color_key(color, 'cable', 'gray'), color)
        generate_cable_models(color)

        generate_simple_loot_table(get_color_key(color, 'grid'), 'refinedstorage2:' + get_color_key(color, 'grid'))
        generate_simple_loot_table(get_color_key(color, 'controller'), 'refinedstorage2:' + get_color_key(color, 'controller'))
        generate_simple_loot_table(get_color_key(color, 'creative_controller'), 'refinedstorage2:' + get_color_key(color, 'creative_controller'))
        generate_simple_loot_table(get_color_key(color, 'cable', 'gray'), 'refinedstorage2:' + get_color_key(color, 'cable', 'gray'))

        generate_recipe('coloring/' + get_color_key(color, 'grid'), {
            'type': 'minecraft:crafting_shapeless',
            'ingredients': [
                {
                    'tag': 'refinedstorage2:grids'
                },
                {
                    'item': 'minecraft:' + dye
                }
            ],
            'result': {
                'item': 'refinedstorage2:' + get_color_key(color, 'grid')
            }
        })

        generate_recipe('coloring/' + get_color_key(color, 'controller'), {
            'type': 'minecraft:crafting_shapeless',
            'ingredients': [
                {
                    'tag': 'refinedstorage2:controllers'
                },
                {
                    'item': 'minecraft:' + dye
                }
            ],
            'result': {
                'item': 'refinedstorage2:' + get_color_key(color, 'controller')
            }
        })

        cable_name = get_color_key(color, 'cable', 'gray')
        generate_recipe('coloring/' + cable_name, {
            'type': 'minecraft:crafting_shapeless',
            'ingredients': [
                {
                    'tag': 'refinedstorage2:cables'
                },
                {
                    'item': 'minecraft:' + dye
                }
            ],
            'result': {
                'item': 'refinedstorage2:' + cable_name
            }
        })

    generate_item_tag('grids', {
        'replace': False,
        'values': list(map(lambda color: 'refinedstorage2:' + get_color_key(color, 'grid'), color_names))
    })

    generate_item_tag('cables', {
        'replace': False,
        'values': list(map(lambda color: 'refinedstorage2:' + get_color_key(color, 'cable', 'gray'), color_names))
    })

    generate_item_tag('storage_disks', {
        'replace': False,
        'values': [
            'refinedstorage2:1k_storage_disk',
            'refinedstorage2:4k_storage_disk',
            'refinedstorage2:16k_storage_disk',
            'refinedstorage2:64k_storage_disk'
        ]
    })

    generate_item_tag('fluid_storage_disks', {
        'replace': False,
        'values': [
            'refinedstorage2:64b_fluid_storage_disk',
            'refinedstorage2:256b_fluid_storage_disk',
            'refinedstorage2:1024b_fluid_storage_disk',
            'refinedstorage2:4096b_fluid_storage_disk'
        ]
    })

    generate_item_tag('controllers', {
        'replace': False,
        'values': list(map(lambda color: 'refinedstorage2:' + get_color_key(color, 'controller'), color_names))
    })

    generate_north_cutout_block_model('grid/inactive', particle='refinedstorage2:block/grid/right', east='refinedstorage2:block/grid/right', south='refinedstorage2:block/grid/back', west='refinedstorage2:block/grid/left',
                                      up='refinedstorage2:block/grid/top', down='refinedstorage2:block/bottom', north='refinedstorage2:block/grid/front', cutout='refinedstorage2:block/grid/cutouts/inactive', emissive_cutout=False)
