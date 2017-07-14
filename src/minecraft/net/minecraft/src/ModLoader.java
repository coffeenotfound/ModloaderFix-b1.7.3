// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            Achievement, StatBase, StringTranslate, BaseMod, 
//            TextureFX, Item, Block, ItemStack, 
//            CraftingManager, FurnaceRecipes, BiomeGenBase, EnumCreatureType, 
//            SpawnListEntry, EntityLiving, EntityRendererProxy, EntityList, 
//            Session, TileEntityRenderer, RenderPlayer, RenderEngine, 
//            BiomeGenHell, BiomeGenSky, TileEntity, RenderBlocks, 
//            GameSettings, StatList, StatCrafting, IRecipe, 
//            TexturePackList, TexturePackBase, EntityPlayer, World, 
//            KeyBinding, IChunkProvider, ModTextureStatic, ItemBlock, 
//            TileEntitySpecialRenderer, MLProp, UnexpectedThrowable, GuiScreen, 
//            IBlockAccess

public final class ModLoader
{

	public static void AddAchievementDesc(Achievement achievement, String s, String s1)
	{
		try
		{
			if(achievement.statName.contains("."))
			{
				String as[] = achievement.statName.split("\\.");
				if(as.length == 2)
				{
					String s2 = as[1];
					AddLocalization((new StringBuilder("achievement.")).append(s2).toString(), s);
					AddLocalization((new StringBuilder("achievement.")).append(s2).append(".desc").toString(), s1);
					setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, StringTranslate.getInstance().translateKey((new StringBuilder("achievement.")).append(s2).toString()));
					setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, StringTranslate.getInstance().translateKey((new StringBuilder("achievement.")).append(s2).append(".desc").toString()));
				} else
				{
					setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, s);
					setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, s1);
				}
			} else
			{
				setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, s);
				setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, s1);
			}
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", illegalargumentexception);
			ThrowException(illegalargumentexception);
		}
		catch(SecurityException securityexception)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", securityexception);
			ThrowException(securityexception);
		}
		catch(NoSuchFieldException nosuchfieldexception)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", nosuchfieldexception);
			ThrowException(nosuchfieldexception);
		}
	}

	public static int AddAllFuel(int i)
	{
		logger.finest((new StringBuilder("Finding fuel for ")).append(i).toString());
		int j = 0;
		for(Iterator iterator = modList.iterator(); iterator.hasNext() && j == 0; j = ((BaseMod)iterator.next()).AddFuel(i)) { }
		if(j != 0)
		{
			logger.finest((new StringBuilder("Returned ")).append(j).toString());
		}
		return j;
	}

	public static void AddAllRenderers(Map map)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		BaseMod basemod;
		for(Iterator iterator = modList.iterator(); iterator.hasNext(); basemod.AddRenderer(map))
		{
			basemod = (BaseMod)iterator.next();
		}

	}

	public static void addAnimation(TextureFX texturefx)
	{
		logger.finest((new StringBuilder("Adding animation ")).append(texturefx.toString()).toString());
		for(Iterator iterator = animList.iterator(); iterator.hasNext();)
		{
			TextureFX texturefx1 = (TextureFX)iterator.next();
			if(texturefx1.tileImage == texturefx.tileImage && texturefx1.iconIndex == texturefx.iconIndex)
			{
				animList.remove(texturefx);
				break;
			}
		}

		animList.add(texturefx);
	}

	public static int AddArmor(String s)
	{
		try
		{
			String as[] = (String[])field_armorList.get(null);
			List list = Arrays.asList(as);
			ArrayList arraylist = new ArrayList();
			arraylist.addAll(list);
			if(!arraylist.contains(s))
			{
				arraylist.add(s);
			}
			int i = arraylist.indexOf(s);
			field_armorList.set(null, ((Object) (arraylist.toArray(new String[0]))));
			return i;
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "AddArmor", illegalargumentexception);
			ThrowException("An impossible error has occured!", illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "AddArmor", illegalaccessexception);
			ThrowException("An impossible error has occured!", illegalaccessexception);
		}
		return -1;
	}

	public static void AddLocalization(String s, String s1)
	{
		Properties properties = null;
		try
		{
			properties = (Properties)getPrivateValue(net.minecraft.src.StringTranslate.class, StringTranslate.getInstance(), 1);
		}
		catch(SecurityException securityexception)
		{
			logger.throwing("ModLoader", "AddLocalization", securityexception);
			ThrowException(securityexception);
		}
		catch(NoSuchFieldException nosuchfieldexception)
		{
			logger.throwing("ModLoader", "AddLocalization", nosuchfieldexception);
			ThrowException(nosuchfieldexception);
		}
		if(properties != null)
		{
			properties.put(s, s1);
		}
	}

	private static void addMod(ClassLoader classloader, String s)
	{
		try
		{
			String s1 = s.split("\\.")[0];
			if(s1.contains("$"))
			{
				return;
			}
			if(props.containsKey(s1) && (props.getProperty(s1).equalsIgnoreCase("no") || props.getProperty(s1).equalsIgnoreCase("off")))
			{
				return;
			}
			Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
			if(package1 != null)
			{
				s1 = (new StringBuilder(String.valueOf(package1.getName()))).append(".").append(s1).toString();
			}
			Class class1 = classloader.loadClass(s1);
			if(!(net.minecraft.src.BaseMod.class).isAssignableFrom(class1))
			{
				return;
			}
			setupProperties(class1);
			BaseMod basemod = (BaseMod)class1.newInstance();
			if(basemod != null)
			{
				modList.add(basemod);
				logger.fine((new StringBuilder("Mod Loaded: \"")).append(basemod.toString()).append("\" from ").append(s).toString());
				System.out.println((new StringBuilder("Mod Loaded: ")).append(basemod.toString()).toString());
			}
		}
		catch(Throwable throwable)
		{
			logger.fine((new StringBuilder("Failed to load mod from \"")).append(s).append("\"").toString());
			System.out.println((new StringBuilder("Failed to load mod from \"")).append(s).append("\"").toString());
			logger.throwing("ModLoader", "addMod", throwable);
			ThrowException(throwable);
		}
	}

	public static void AddName(Object obj, String s)
	{
		String s1 = null;
		if(obj instanceof Item)
		{
			Item item = (Item)obj;
			if(item.getItemName() != null)
			{
				s1 = (new StringBuilder(String.valueOf(item.getItemName()))).append(".name").toString();
			}
		} else
			if(obj instanceof Block)
			{
				Block block = (Block)obj;
				if(block.getBlockName() != null)
				{
					s1 = (new StringBuilder(String.valueOf(block.getBlockName()))).append(".name").toString();
				}
			} else
				if(obj instanceof ItemStack)
				{
					ItemStack itemstack = (ItemStack)obj;
					if(itemstack.getItemName() != null)
					{
						s1 = (new StringBuilder(String.valueOf(itemstack.getItemName()))).append(".name").toString();
					}
				} else
				{
					Exception exception = new Exception((new StringBuilder(String.valueOf(obj.getClass().getName()))).append(" cannot have name attached to it!").toString());
					logger.throwing("ModLoader", "AddName", exception);
					ThrowException(exception);
				}
		if(s1 != null)
		{
			AddLocalization(s1, s);
		} else
		{
			Exception exception1 = new Exception((new StringBuilder()).append(obj).append(" is missing name tag!").toString());
			logger.throwing("ModLoader", "AddName", exception1);
			ThrowException(exception1);
		}
	}

	public static int addOverride(String s, String s1)
	{
		try
		{
			int i = getUniqueSpriteIndex(s);
			addOverride(s, s1, i);
			return i;
		}
		catch(Throwable throwable)
		{
			logger.throwing("ModLoader", "addOverride", throwable);
			ThrowException(throwable);
			throw new RuntimeException(throwable);
		}
	}

	public static void addOverride(String s, String s1, int i)
	{
		int j = -1;
		int k = 0;
		if(s.equals("/terrain.png"))
		{
			j = 0;
			k = terrainSpritesLeft;
		} else
			if(s.equals("/gui/items.png"))
			{
				j = 1;
				k = itemSpritesLeft;
			} else
			{
				return;
			}
		System.out.println((new StringBuilder("Overriding ")).append(s).append(" with ").append(s1).append(" @ ").append(i).append(". ").append(k).append(" left.").toString());
		logger.finer((new StringBuilder("addOverride(")).append(s).append(",").append(s1).append(",").append(i).append("). ").append(k).append(" left.").toString());
		Object obj = (Map)overrides.get(Integer.valueOf(j));
		if(obj == null)
		{
			obj = new HashMap();
			overrides.put(Integer.valueOf(j), obj);
		}
		((Map) (obj)).put(s1, Integer.valueOf(i));
	}

	public static void AddRecipe(ItemStack itemstack, Object aobj[])
	{
		CraftingManager.getInstance().addRecipe(itemstack, aobj);
	}

	public static void AddShapelessRecipe(ItemStack itemstack, Object aobj[])
	{
		CraftingManager.getInstance().addShapelessRecipe(itemstack, aobj);
	}

	public static void AddSmelting(int i, ItemStack itemstack)
	{
		FurnaceRecipes.smelting().addSmelting(i, itemstack);
	}

	public static void AddSpawn(Class class1, int i, EnumCreatureType enumcreaturetype)
	{
		AddSpawn(class1, i, enumcreaturetype, null);
	}

	public static void AddSpawn(Class class1, int i, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
	{
		if(class1 == null)
		{
			throw new IllegalArgumentException("entityClass cannot be null");
		}
		if(enumcreaturetype == null)
		{
			throw new IllegalArgumentException("spawnList cannot be null");
		}
		if(abiomegenbase == null)
		{
			abiomegenbase = standardBiomes;
		}
		for(int j = 0; j < abiomegenbase.length; j++)
		{
			List list = abiomegenbase[j].getSpawnableList(enumcreaturetype);
			if(list != null)
			{
				boolean flag = false;
				for(Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();
					if(spawnlistentry.entityClass == class1)
					{
						spawnlistentry.spawnRarityRate = i;
						flag = true;
						break;
					}
				}

				if(!flag)
				{
					list.add(new SpawnListEntry(class1, i));
				}
			}
		}

	}

	public static void AddSpawn(String s, int i, EnumCreatureType enumcreaturetype)
	{
		AddSpawn(s, i, enumcreaturetype, null);
	}

	public static void AddSpawn(String s, int i, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
	{
		Class class1 = (Class)classMap.get(s);
		if(class1 != null && (net.minecraft.src.EntityLiving.class).isAssignableFrom(class1))
		{
			AddSpawn(class1, i, enumcreaturetype, abiomegenbase);
		}
	}

	public static boolean DispenseEntity(World world, double d, double d1, double d2, int i, 
			int j, ItemStack itemstack)
	{
		boolean flag = false;
		for(Iterator iterator = modList.iterator(); iterator.hasNext() && !flag; flag = ((BaseMod)iterator.next()).DispenseEntity(world, d, d1, d2, i, j, itemstack)) { }
		return flag;
	}

	public static List getLoadedMods()
	{
		return Collections.unmodifiableList(modList);
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static Minecraft getMinecraftInstance()
	{
		if(instance == null)
		{
			try
			{
				ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
				int i = threadgroup.activeCount();
				Thread athread[] = new Thread[i];
				threadgroup.enumerate(athread);
				for(int j = 0; j < athread.length; j++)
				{
					if(!athread[j].getName().equals("Minecraft main thread"))
					{
						continue;
					}
					instance = (Minecraft)getPrivateValue(java.lang.Thread.class, athread[j], "target");
					break;
				}

			}
			catch(SecurityException securityexception)
			{
				logger.throwing("ModLoader", "getMinecraftInstance", securityexception);
				throw new RuntimeException(securityexception);
			}
			catch(NoSuchFieldException nosuchfieldexception)
			{
				logger.throwing("ModLoader", "getMinecraftInstance", nosuchfieldexception);
				throw new RuntimeException(nosuchfieldexception);
			}
		}
		return instance;
	}

	public static Object getPrivateValue(Class class1, Object obj, int i)
			throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field field = class1.getDeclaredFields()[i];
			field.setAccessible(true);
			return field.get(obj);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "getPrivateValue", illegalaccessexception);
			ThrowException("An impossible error has occured!", illegalaccessexception);
			return null;
		}
	}

	public static Object getPrivateValue(Class class1, Object obj, String s)
			throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field field = class1.getDeclaredField(s);
			field.setAccessible(true);
			return field.get(obj);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "getPrivateValue", illegalaccessexception);
			ThrowException("An impossible error has occured!", illegalaccessexception);
			return null;
		}
	}

	public static int getUniqueBlockModelID(BaseMod basemod, boolean flag)
	{
		int i = nextBlockModelID++;
		blockModels.put(Integer.valueOf(i), basemod);
		blockSpecialInv.put(Integer.valueOf(i), Boolean.valueOf(flag));
		return i;
	}

	public static int getUniqueEntityId()
	{
		return highestEntityId++;
	}

	private static int getUniqueItemSpriteIndex()
	{
		for(; itemSpriteIndex < usedItemSprites.length; itemSpriteIndex++)
		{
			if(!usedItemSprites[itemSpriteIndex])
			{
				usedItemSprites[itemSpriteIndex] = true;
				itemSpritesLeft--;
				return itemSpriteIndex++;
			}
		}

		Exception exception = new Exception("No more empty item sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
		ThrowException(exception);
		return 0;
	}

	public static int getUniqueSpriteIndex(String s)
	{
		if(s.equals("/gui/items.png"))
		{
			return getUniqueItemSpriteIndex();
		}
		if(s.equals("/terrain.png"))
		{
			return getUniqueTerrainSpriteIndex();
		} else
		{
			Exception exception = new Exception((new StringBuilder("No registry for this texture: ")).append(s).toString());
			logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
			ThrowException(exception);
			return 0;
		}
	}

	private static int getUniqueTerrainSpriteIndex()
	{
		for(; terrainSpriteIndex < usedTerrainSprites.length; terrainSpriteIndex++)
		{
			if(!usedTerrainSprites[terrainSpriteIndex])
			{
				usedTerrainSprites[terrainSpriteIndex] = true;
				terrainSpritesLeft--;
				return terrainSpriteIndex++;
			}
		}

		Exception exception = new Exception("No more empty terrain sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
		ThrowException(exception);
		return 0;
	}

	private static void init()
	{
		hasInit = true;
		String s = "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111011111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
		String s1 = "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111111110111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011";
		for(int i = 0; i < 256; i++)
		{
			usedItemSprites[i] = s.charAt(i) == '1';
			if(!usedItemSprites[i])
			{
				itemSpritesLeft++;
			}
			usedTerrainSprites[i] = s1.charAt(i) == '1';
			if(!usedTerrainSprites[i])
			{
				terrainSpritesLeft++;
			}
		}

		try
		{
			instance = (Minecraft)getPrivateValue(net.minecraft.client.Minecraft.class, null, 1);
			instance.entityRenderer = new EntityRendererProxy(instance);
			classMap = (Map)getPrivateValue(net.minecraft.src.EntityList.class, null, 0);
			field_modifiers = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
			field_modifiers.setAccessible(true);
			field_blockList = (net.minecraft.src.Session.class).getDeclaredFields()[0];
			field_blockList.setAccessible(true);
			field_TileEntityRenderers = (net.minecraft.src.TileEntityRenderer.class).getDeclaredFields()[0];
			field_TileEntityRenderers.setAccessible(true);
			field_armorList = (net.minecraft.src.RenderPlayer.class).getDeclaredFields()[3];
			field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & 0xffffffef);
			field_armorList.setAccessible(true);
			field_animList = (net.minecraft.src.RenderEngine.class).getDeclaredFields()[6];
			field_animList.setAccessible(true);
			Field afield[] = (net.minecraft.src.BiomeGenBase.class).getDeclaredFields();
			LinkedList linkedlist = new LinkedList();
			for(int j = 0; j < afield.length; j++)
			{
				Class class1 = afield[j].getType();
				if((afield[j].getModifiers() & 8) != 0 && class1.isAssignableFrom(net.minecraft.src.BiomeGenBase.class))
				{
					BiomeGenBase biomegenbase = (BiomeGenBase)afield[j].get(null);
					if(!(biomegenbase instanceof BiomeGenHell) && !(biomegenbase instanceof BiomeGenSky))
					{
						linkedlist.add(biomegenbase);
					}
				}
			}

			standardBiomes = (BiomeGenBase[])linkedlist.toArray(new BiomeGenBase[0]);
			try
			{
				method_RegisterTileEntity = (net.minecraft.src.TileEntity.class).getDeclaredMethod("a", new Class[] {
						java.lang.Class.class, java.lang.String.class
				});
			}
			catch(NoSuchMethodException nosuchmethodexception1)
			{
				method_RegisterTileEntity = (net.minecraft.src.TileEntity.class).getDeclaredMethod("addMapping", new Class[] {
						java.lang.Class.class, java.lang.String.class
				});
			}
			method_RegisterTileEntity.setAccessible(true);
			try
			{
				method_RegisterEntityID = (net.minecraft.src.EntityList.class).getDeclaredMethod("a", new Class[] {
						java.lang.Class.class, java.lang.String.class, Integer.TYPE
				});
			}
			catch(NoSuchMethodException nosuchmethodexception2)
			{
				method_RegisterEntityID = (net.minecraft.src.EntityList.class).getDeclaredMethod("addMapping", new Class[] {
						java.lang.Class.class, java.lang.String.class, Integer.TYPE
				});
			}
			method_RegisterEntityID.setAccessible(true);
		}
		catch(SecurityException securityexception)
		{
			logger.throwing("ModLoader", "init", securityexception);
			ThrowException(securityexception);
			throw new RuntimeException(securityexception);
		}
		catch(NoSuchFieldException nosuchfieldexception)
		{
			logger.throwing("ModLoader", "init", nosuchfieldexception);
			ThrowException(nosuchfieldexception);
			throw new RuntimeException(nosuchfieldexception);
		}
		catch(NoSuchMethodException nosuchmethodexception)
		{
			logger.throwing("ModLoader", "init", nosuchmethodexception);
			ThrowException(nosuchmethodexception);
			throw new RuntimeException(nosuchmethodexception);
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "init", illegalargumentexception);
			ThrowException(illegalargumentexception);
			throw new RuntimeException(illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "init", illegalaccessexception);
			ThrowException(illegalaccessexception);
			throw new RuntimeException(illegalaccessexception);
		}
		try
		{
			loadConfig();
			if(props.containsKey("loggingLevel"))
			{
				cfgLoggingLevel = Level.parse(props.getProperty("loggingLevel"));
			}
			if(props.containsKey("grassFix"))
			{
				RenderBlocks.cfgGrassFix = Boolean.parseBoolean(props.getProperty("grassFix"));
			}
			logger.setLevel(cfgLoggingLevel);
			if((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null)
			{
				logHandler = new FileHandler(logfile.getPath());
				logHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(logHandler);
			}
			logger.fine("ModLoader Beta 1.7.3 Initializing...");
			System.out.println("ModLoader Beta 1.7.3 Initializing...");
			
//			File file = new File((net.minecraft.src.ModLoader.class).getProtectionDomain().getCodeSource().getLocation().toURI());
			File file;
			{// resolve jar path | fixed by coffeenotfound @ 2017
				System.out.println("***** Modloader Fix by coffeenotfound @ 2017 ~ https://github.com/coffeenotfound ~ https://bitangent.net/ ****");
				try {
					String rawJarPath = URLDecoder.decode(net.minecraft.src.ModLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
					
					// fix path
					rawJarPath = rawJarPath.replace("jar:", "").replace("file:/", "").replace("file:\\", ""); /* replace scheme part */
					if(rawJarPath.contains(".jar!")) {
						rawJarPath = rawJarPath.substring(0, rawJarPath.lastIndexOf(".jar!") + ".jar".length());
					}
					
					// cleanup
					rawJarPath = new File(rawJarPath).getAbsolutePath();
					
					// log
					System.out.println("[Modloader Fix] original jar path = " + ((net.minecraft.src.ModLoader.class).getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
					System.out.println("[Modloader Fix] new jar path = " + rawJarPath);
					
					// set file
					file = new File(rawJarPath);
				}
				catch(Exception e) {
					throw new RuntimeException("Failed to resolve minecraft jar path; this is the fixed code, if this exception ever gets thrown I owe you a beer.", e);
				}
			}

			modDir.mkdirs();
			readFromModFolder(modDir);
			readFromClassPath(file);
			System.out.println("Done.");
			props.setProperty("loggingLevel", cfgLoggingLevel.getName());
			props.setProperty("grassFix", Boolean.toString(RenderBlocks.cfgGrassFix));
			for(Iterator iterator = modList.iterator(); iterator.hasNext();)
			{
				BaseMod basemod = (BaseMod)iterator.next();
				basemod.ModsLoaded();
				if(!props.containsKey(basemod.getClass().getName()))
				{
					props.setProperty(basemod.getClass().getName(), "on");
				}
			}

			instance.gameSettings.keyBindings = RegisterAllKeys(instance.gameSettings.keyBindings);
			instance.gameSettings.loadOptions();
			initStats();
			saveConfig();
		}
		catch(Throwable throwable)
		{
			logger.throwing("ModLoader", "init", throwable);
			ThrowException("ModLoader has failed to initialize.", throwable);
			if(logHandler != null)
			{
				logHandler.close();
			}
			throw new RuntimeException(throwable);
		}
	}

	private static void initStats()
	{
		for(int i = 0; i < Block.blocksList.length; i++)
		{
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1000000 + i)) && Block.blocksList[i] != null && Block.blocksList[i].getEnableStats())
			{
				String s = StringTranslate.getInstance().translateKeyFormat("stat.mineBlock", new Object[] {
						Block.blocksList[i].translateBlockName()
				});
				StatList.mineBlockStatArray[i] = (new StatCrafting(0x1000000 + i, s, i)).registerStat();
				StatList.field_25185_d.add(StatList.mineBlockStatArray[i]);
			}
		}

		for(int j = 0; j < Item.itemsList.length; j++)
		{
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1020000 + j)) && Item.itemsList[j] != null)
			{
				String s1 = StringTranslate.getInstance().translateKeyFormat("stat.useItem", new Object[] {
						Item.itemsList[j].getStatName()
				});
				StatList.field_25172_A[j] = (new StatCrafting(0x1020000 + j, s1, j)).registerStat();
				if(j >= Block.blocksList.length)
				{
					StatList.field_25186_c.add(StatList.field_25172_A[j]);
				}
			}
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1030000 + j)) && Item.itemsList[j] != null && Item.itemsList[j].isDamagable())
			{
				String s2 = StringTranslate.getInstance().translateKeyFormat("stat.breakItem", new Object[] {
						Item.itemsList[j].getStatName()
				});
				StatList.field_25170_B[j] = (new StatCrafting(0x1030000 + j, s2, j)).registerStat();
			}
		}

		HashSet hashset = new HashSet();
		Object obj;
		for(Iterator iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext(); hashset.add(Integer.valueOf(((IRecipe)obj).getRecipeOutput().itemID)))
		{
			obj = iterator.next();
		}

		Object obj1;
		for(Iterator iterator1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); iterator1.hasNext(); hashset.add(Integer.valueOf(((ItemStack)obj1).itemID)))
		{
			obj1 = iterator1.next();
		}

		for(Iterator iterator2 = hashset.iterator(); iterator2.hasNext();)
		{
			int k = ((Integer)iterator2.next()).intValue();
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1010000 + k)) && Item.itemsList[k] != null)
			{
				String s3 = StringTranslate.getInstance().translateKeyFormat("stat.craftItem", new Object[] {
						Item.itemsList[k].getStatName()
				});
				StatList.field_25158_z[k] = (new StatCrafting(0x1010000 + k, s3, k)).registerStat();
			}
		}

	}

	public static boolean isGUIOpen(Class class1)
	{
		Minecraft minecraft = getMinecraftInstance();
		if(class1 == null)
		{
			return minecraft.currentScreen == null;
		}
		if(minecraft.currentScreen == null && class1 != null)
		{
			return false;
		} else
		{
			return class1.isInstance(minecraft.currentScreen);
		}
	}

	public static boolean isModLoaded(String s)
	{
		Class class1 = null;
		try
		{
			class1 = Class.forName(s);
		}
		catch(ClassNotFoundException classnotfoundexception)
		{
			return false;
		}
		if(class1 != null)
		{
			for(Iterator iterator = modList.iterator(); iterator.hasNext();)
			{
				BaseMod basemod = (BaseMod)iterator.next();
				if(class1.isInstance(basemod))
				{
					return true;
				}
			}

		}
		return false;
	}

	public static void loadConfig()
			throws IOException
	{
		cfgdir.mkdir();
		if(!cfgfile.exists() && !cfgfile.createNewFile())
		{
			return;
		}
		if(cfgfile.canRead())
		{
			FileInputStream fileinputstream = new FileInputStream(cfgfile);
			props.load(fileinputstream);
			fileinputstream.close();
		}
	}

	public static java.awt.image.BufferedImage loadImage(RenderEngine renderengine, String s)
			throws Exception
	{
		TexturePackList texturepacklist = (TexturePackList)getPrivateValue(net.minecraft.src.RenderEngine.class, renderengine, 11);
		InputStream inputstream = texturepacklist.selectedTexturePack.getResourceAsStream(s);
		if(inputstream == null)
		{
			throw new Exception((new StringBuilder("Image not found: ")).append(s).toString());
		}
		java.awt.image.BufferedImage bufferedimage = ImageIO.read(inputstream);
		if(bufferedimage == null)
		{
			throw new Exception((new StringBuilder("Image corrupted: ")).append(s).toString());
		} else
		{
			return bufferedimage;
		}
	}

	public static void OnItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
	{
		BaseMod basemod;
		for(Iterator iterator = modList.iterator(); iterator.hasNext(); basemod.OnItemPickup(entityplayer, itemstack))
		{
			basemod = (BaseMod)iterator.next();
		}

	}

	public static void OnTick(Minecraft minecraft)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		if(texPack == null || minecraft.gameSettings.skin != texPack)
		{
			texturesAdded = false;
			texPack = minecraft.gameSettings.skin;
		}
		if(!texturesAdded && minecraft.renderEngine != null)
		{
			RegisterAllTextureOverrides(minecraft.renderEngine);
			texturesAdded = true;
		}
		long l = 0L;
		if(minecraft.theWorld != null)
		{
			l = minecraft.theWorld.getWorldTime();
			for(Iterator iterator = inGameHooks.entrySet().iterator(); iterator.hasNext();)
			{
				java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
				if((clock != l || !((Boolean)entry1.getValue()).booleanValue()) && !((BaseMod)entry1.getKey()).OnTickInGame(minecraft))
				{
					iterator.remove();
				}
			}

		}
		if(minecraft.currentScreen != null)
		{
			for(Iterator iterator1 = inGUIHooks.entrySet().iterator(); iterator1.hasNext();)
			{
				java.util.Map.Entry entry2 = (java.util.Map.Entry)iterator1.next();
				if((clock != l || !(((Boolean)entry2.getValue()).booleanValue() & (minecraft.theWorld != null))) && !((BaseMod)entry2.getKey()).OnTickInGUI(minecraft, minecraft.currentScreen))
				{
					iterator1.remove();
				}
			}

		}
		if(clock != l)
		{
			for(Iterator iterator2 = keyList.entrySet().iterator(); iterator2.hasNext();)
			{
				java.util.Map.Entry entry = (java.util.Map.Entry)iterator2.next();
				for(Iterator iterator3 = ((Map)entry.getValue()).entrySet().iterator(); iterator3.hasNext();)
				{
					java.util.Map.Entry entry3 = (java.util.Map.Entry)iterator3.next();
					boolean flag = Keyboard.isKeyDown(((KeyBinding)entry3.getKey()).keyCode);
					boolean aflag[] = (boolean[])entry3.getValue();
					boolean flag1 = aflag[1];
					aflag[1] = flag;
					if(flag && (!flag1 || aflag[0]))
					{
						((BaseMod)entry.getKey()).KeyboardEvent((KeyBinding)entry3.getKey());
					}
				}

			}

		}
		clock = l;
	}

	public static void OpenGUI(EntityPlayer entityplayer, GuiScreen guiscreen)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		Minecraft minecraft = getMinecraftInstance();
		if(minecraft.thePlayer != entityplayer)
		{
			return;
		}
		if(guiscreen != null)
		{
			minecraft.displayGuiScreen(guiscreen);
		}
	}

	public static void PopulateChunk(IChunkProvider ichunkprovider, int i, int j, World world)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		Random random = new Random(world.getRandomSeed());
		long l = (random.nextLong() / 2L) * 2L + 1L;
		long l1 = (random.nextLong() / 2L) * 2L + 1L;
		random.setSeed((long)i * l + (long)j * l1 ^ world.getRandomSeed());
		for(Iterator iterator = modList.iterator(); iterator.hasNext();)
		{
			BaseMod basemod = (BaseMod)iterator.next();
			if(ichunkprovider.makeString().equals("RandomLevelSource"))
			{
				basemod.GenerateSurface(world, random, i << 4, j << 4);
			} else
				if(ichunkprovider.makeString().equals("HellRandomLevelSource"))
				{
					basemod.GenerateNether(world, random, i << 4, j << 4);
				}
		}

	}

	private static void readFromClassPath(File file)
			throws FileNotFoundException, IOException
	{
		logger.finer((new StringBuilder("Adding mods from ")).append(file.getCanonicalPath()).toString());
		ClassLoader classloader = (net.minecraft.src.ModLoader.class).getClassLoader();
		if(file.isFile() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")))
		{
			logger.finer("Zip found.");
			FileInputStream fileinputstream = new FileInputStream(file);
			ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
			Object obj = null;
			do
			{
				ZipEntry zipentry = zipinputstream.getNextEntry();
				if(zipentry == null)
				{
					break;
				}
				String s1 = zipentry.getName();
				if(!zipentry.isDirectory() && s1.startsWith("mod_") && s1.endsWith(".class"))
				{
					addMod(classloader, s1);
				}
			} while(true);
			fileinputstream.close();
		} else
			if(file.isDirectory())
			{
				Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
				if(package1 != null)
				{
					String s = package1.getName().replace('.', File.separatorChar);
					file = new File(file, s);
				}
				logger.finer("Directory found.");
				File afile[] = file.listFiles();
				if(afile != null)
				{
					for(int i = 0; i < afile.length; i++)
					{
						String s2 = afile[i].getName();
						if(afile[i].isFile() && s2.startsWith("mod_") && s2.endsWith(".class"))
						{
							addMod(classloader, s2);
						}
					}

				}
			}
	}

	private static void readFromModFolder(File file)
			throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		ClassLoader classloader = (net.minecraft.client.Minecraft.class).getClassLoader();
		Method method = (java.net.URLClassLoader.class).getDeclaredMethod("addURL", new Class[] {
				java.net.URL.class
		});
		method.setAccessible(true);
		if(!file.isDirectory())
		{
			throw new IllegalArgumentException("folder must be a Directory.");
		}
		File afile[] = file.listFiles();
		if(classloader instanceof URLClassLoader)
		{
			for(int i = 0; i < afile.length; i++)
			{
				File file1 = afile[i];
				if(file1.isDirectory() || file1.isFile() && (file1.getName().endsWith(".jar") || file1.getName().endsWith(".zip")))
				{
					method.invoke(classloader, new Object[] {
							file1.toURI().toURL()
					});
				}
			}

		}
		for(int j = 0; j < afile.length; j++)
		{
			File file2 = afile[j];
			if(file2.isDirectory() || file2.isFile() && (file2.getName().endsWith(".jar") || file2.getName().endsWith(".zip")))
			{
				logger.finer((new StringBuilder("Adding mods from ")).append(file2.getCanonicalPath()).toString());
				if(file2.isFile())
				{
					logger.finer("Zip found.");
					FileInputStream fileinputstream = new FileInputStream(file2);
					ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
					Object obj = null;
					do
					{
						ZipEntry zipentry = zipinputstream.getNextEntry();
						if(zipentry == null)
						{
							break;
						}
						String s1 = zipentry.getName();
						if(!zipentry.isDirectory() && s1.startsWith("mod_") && s1.endsWith(".class"))
						{
							addMod(classloader, s1);
						}
					} while(true);
					zipinputstream.close();
					fileinputstream.close();
				} else
					if(file2.isDirectory())
					{
						Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
						if(package1 != null)
						{
							String s = package1.getName().replace('.', File.separatorChar);
							file2 = new File(file2, s);
						}
						logger.finer("Directory found.");
						File afile1[] = file2.listFiles();
						if(afile1 != null)
						{
							for(int k = 0; k < afile1.length; k++)
							{
								String s2 = afile1[k].getName();
								if(afile1[k].isFile() && s2.startsWith("mod_") && s2.endsWith(".class"))
								{
									addMod(classloader, s2);
								}
							}

						}
					}
			}
		}

	}

	public static KeyBinding[] RegisterAllKeys(KeyBinding akeybinding[])
	{
		LinkedList linkedlist = new LinkedList();
		linkedlist.addAll(Arrays.asList(akeybinding));
		Map map;
		for(Iterator iterator = keyList.values().iterator(); iterator.hasNext(); linkedlist.addAll(map.keySet()))
		{
			map = (Map)iterator.next();
		}

		return (KeyBinding[])linkedlist.toArray(new KeyBinding[0]);
	}

	public static void RegisterAllTextureOverrides(RenderEngine renderengine)
	{
		animList.clear();
		Minecraft minecraft = getMinecraftInstance();
		BaseMod basemod;
		for(Iterator iterator = modList.iterator(); iterator.hasNext(); basemod.RegisterAnimation(minecraft))
		{
			basemod = (BaseMod)iterator.next();
		}

		TextureFX texturefx;
		for(Iterator iterator1 = animList.iterator(); iterator1.hasNext(); renderengine.registerTextureFX(texturefx))
		{
			texturefx = (TextureFX)iterator1.next();
		}

		for(Iterator iterator2 = overrides.entrySet().iterator(); iterator2.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)iterator2.next();
			for(Iterator iterator3 = ((Map)entry.getValue()).entrySet().iterator(); iterator3.hasNext();)
			{
				java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator3.next();
				String s = (String)entry1.getKey();
				int i = ((Integer)entry1.getValue()).intValue();
				int j = ((Integer)entry.getKey()).intValue();
				try
				{
					java.awt.image.BufferedImage bufferedimage = loadImage(renderengine, s);
					ModTextureStatic modtexturestatic = new ModTextureStatic(i, j, bufferedimage);
					renderengine.registerTextureFX(modtexturestatic);
				}
				catch(Exception exception)
				{
					logger.throwing("ModLoader", "RegisterAllTextureOverrides", exception);
					ThrowException(exception);
					throw new RuntimeException(exception);
				}
			}

		}

	}

	public static void RegisterBlock(Block block)
	{
		RegisterBlock(block, null);
	}

	public static void RegisterBlock(Block block, Class class1)
	{
		try
		{
			if(block == null)
			{
				throw new IllegalArgumentException("block parameter cannot be null.");
			}
			List list = (List)field_blockList.get(null);
			list.add(block);
			int i = block.blockID;
			ItemBlock itemblock = null;
			if(class1 != null)
			{
				itemblock = (ItemBlock)class1.getConstructor(new Class[] {
						Integer.TYPE
				}).newInstance(new Object[] {
						Integer.valueOf(i - 256)
				});
			} else
			{
				itemblock = new ItemBlock(i - 256);
			}
			if(Block.blocksList[i] != null && Item.itemsList[i] == null)
			{
				Item.itemsList[i] = itemblock;
			}
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", illegalargumentexception);
			ThrowException(illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", illegalaccessexception);
			ThrowException(illegalaccessexception);
		}
		catch(SecurityException securityexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", securityexception);
			ThrowException(securityexception);
		}
		catch(InstantiationException instantiationexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", instantiationexception);
			ThrowException(instantiationexception);
		}
		catch(InvocationTargetException invocationtargetexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", invocationtargetexception);
			ThrowException(invocationtargetexception);
		}
		catch(NoSuchMethodException nosuchmethodexception)
		{
			logger.throwing("ModLoader", "RegisterBlock", nosuchmethodexception);
			ThrowException(nosuchmethodexception);
		}
	}

	public static void RegisterEntityID(Class class1, String s, int i)
	{
		try
		{
			method_RegisterEntityID.invoke(null, new Object[] {
					class1, s, Integer.valueOf(i)
			});
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", illegalargumentexception);
			ThrowException(illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", illegalaccessexception);
			ThrowException(illegalaccessexception);
		}
		catch(InvocationTargetException invocationtargetexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", invocationtargetexception);
			ThrowException(invocationtargetexception);
		}
	}

	public static void RegisterKey(BaseMod basemod, KeyBinding keybinding, boolean flag)
	{
		Object obj = (Map)keyList.get(basemod);
		if(obj == null)
		{
			obj = new HashMap();
		}
		boolean aflag[] = new boolean[2];
		aflag[0] = flag;
		((Map) (obj)).put(keybinding, aflag);
		keyList.put(basemod, obj);
	}

	public static void RegisterTileEntity(Class class1, String s)
	{
		RegisterTileEntity(class1, s, null);
	}

	public static void RegisterTileEntity(Class class1, String s, TileEntitySpecialRenderer tileentityspecialrenderer)
	{
		try
		{
			method_RegisterTileEntity.invoke(null, new Object[] {
					class1, s
			});
			if(tileentityspecialrenderer != null)
			{
				TileEntityRenderer tileentityrenderer = TileEntityRenderer.instance;
				Map map = (Map)field_TileEntityRenderers.get(tileentityrenderer);
				map.put(class1, tileentityspecialrenderer);
				tileentityspecialrenderer.setTileEntityRenderer(tileentityrenderer);
			}
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "RegisterTileEntity", illegalargumentexception);
			ThrowException(illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "RegisterTileEntity", illegalaccessexception);
			ThrowException(illegalaccessexception);
		}
		catch(InvocationTargetException invocationtargetexception)
		{
			logger.throwing("ModLoader", "RegisterTileEntity", invocationtargetexception);
			ThrowException(invocationtargetexception);
		}
	}

	public static void RemoveSpawn(Class class1, EnumCreatureType enumcreaturetype)
	{
		RemoveSpawn(class1, enumcreaturetype, null);
	}

	public static void RemoveSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
	{
		if(class1 == null)
		{
			throw new IllegalArgumentException("entityClass cannot be null");
		}
		if(enumcreaturetype == null)
		{
			throw new IllegalArgumentException("spawnList cannot be null");
		}
		if(abiomegenbase == null)
		{
			abiomegenbase = standardBiomes;
		}
		for(int i = 0; i < abiomegenbase.length; i++)
		{
			List list = abiomegenbase[i].getSpawnableList(enumcreaturetype);
			if(list != null)
			{
				for(Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();
					if(spawnlistentry.entityClass == class1)
					{
						iterator.remove();
					}
				}

			}
		}

	}

	public static void RemoveSpawn(String s, EnumCreatureType enumcreaturetype)
	{
		RemoveSpawn(s, enumcreaturetype, null);
	}

	public static void RemoveSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
	{
		Class class1 = (Class)classMap.get(s);
		if(class1 != null && (net.minecraft.src.EntityLiving.class).isAssignableFrom(class1))
		{
			RemoveSpawn(class1, enumcreaturetype, abiomegenbase);
		}
	}

	public static boolean RenderBlockIsItemFull3D(int i)
	{
		if(!blockSpecialInv.containsKey(Integer.valueOf(i)))
		{
			return i == 16;
		} else
		{
			return ((Boolean)blockSpecialInv.get(Integer.valueOf(i))).booleanValue();
		}
	}

	public static void RenderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
	{
		BaseMod basemod = (BaseMod)blockModels.get(Integer.valueOf(j));
		if(basemod == null)
		{
			return;
		} else
		{
			basemod.RenderInvBlock(renderblocks, block, i, j);
			return;
		}
	}

	public static boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
	{
		BaseMod basemod = (BaseMod)blockModels.get(Integer.valueOf(l));
		if(basemod == null)
		{
			return false;
		} else
		{
			return basemod.RenderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
		}
	}

	public static void saveConfig()
			throws IOException
	{
		cfgdir.mkdir();
		if(!cfgfile.exists() && !cfgfile.createNewFile())
		{
			return;
		}
		if(cfgfile.canWrite())
		{
			FileOutputStream fileoutputstream = new FileOutputStream(cfgfile);
			props.store(fileoutputstream, "ModLoader Config");
			fileoutputstream.close();
		}
	}

	public static void SetInGameHook(BaseMod basemod, boolean flag, boolean flag1)
	{
		if(flag)
		{
			inGameHooks.put(basemod, Boolean.valueOf(flag1));
		} else
		{
			inGameHooks.remove(basemod);
		}
	}

	public static void SetInGUIHook(BaseMod basemod, boolean flag, boolean flag1)
	{
		if(flag)
		{
			inGUIHooks.put(basemod, Boolean.valueOf(flag1));
		} else
		{
			inGUIHooks.remove(basemod);
		}
	}

	public static void setPrivateValue(Class class1, Object obj, int i, Object obj1)
			throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field field = class1.getDeclaredFields()[i];
			field.setAccessible(true);
			int j = field_modifiers.getInt(field);
			if((j & 0x10) != 0)
			{
				field_modifiers.setInt(field, j & 0xffffffef);
			}
			field.set(obj, obj1);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "setPrivateValue", illegalaccessexception);
			ThrowException("An impossible error has occured!", illegalaccessexception);
		}
	}

	public static void setPrivateValue(Class class1, Object obj, String s, Object obj1)
			throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field field = class1.getDeclaredField(s);
			int i = field_modifiers.getInt(field);
			if((i & 0x10) != 0)
			{
				field_modifiers.setInt(field, i & 0xffffffef);
			}
			field.setAccessible(true);
			field.set(obj, obj1);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "setPrivateValue", illegalaccessexception);
			ThrowException("An impossible error has occured!", illegalaccessexception);
		}
	}

	private static void setupProperties(Class class1)
			throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException
	{
		Properties properties = new Properties();
		File file = new File(cfgdir, (new StringBuilder(String.valueOf(class1.getName()))).append(".cfg").toString());
		if(file.exists() && file.canRead())
		{
			properties.load(new FileInputStream(file));
		}
		StringBuilder stringbuilder = new StringBuilder();
		Field afield[];
		int j = (afield = class1.getFields()).length;
		for(int i = 0; i < j; i++)
		{
			Field field = afield[i];
			if((field.getModifiers() & 8) == 0 || !field.isAnnotationPresent(net.minecraft.src.MLProp.class))
			{
				continue;
			}
			Class class2 = field.getType();
			MLProp mlprop = (MLProp)field.getAnnotation(net.minecraft.src.MLProp.class);
			String s = mlprop.name().length() != 0 ? mlprop.name() : field.getName();
			Object obj = field.get(null);
			StringBuilder stringbuilder1 = new StringBuilder();
			if(mlprop.min() != (-1.0D / 0.0D))
			{
				stringbuilder1.append(String.format(",>=%.1f", new Object[] {
						Double.valueOf(mlprop.min())
				}));
			}
			if(mlprop.max() != (1.0D / 0.0D))
			{
				stringbuilder1.append(String.format(",<=%.1f", new Object[] {
						Double.valueOf(mlprop.max())
				}));
			}
			StringBuilder stringbuilder2 = new StringBuilder();
			if(mlprop.info().length() > 0)
			{
				stringbuilder2.append(" -- ");
				stringbuilder2.append(mlprop.info());
			}
			stringbuilder.append(String.format("%s (%s:%s%s)%s\n", new Object[] {
					s, class2.getName(), obj, stringbuilder1, stringbuilder2
			}));
			if(properties.containsKey(s))
			{
				String s1 = properties.getProperty(s);
				Object obj1 = null;
				if(class2.isAssignableFrom(java.lang.String.class))
				{
					obj1 = s1;
				} else
					if(class2.isAssignableFrom(Integer.TYPE))
					{
						obj1 = Integer.valueOf(Integer.parseInt(s1));
					} else
						if(class2.isAssignableFrom(Short.TYPE))
						{
							obj1 = Short.valueOf(Short.parseShort(s1));
						} else
							if(class2.isAssignableFrom(Byte.TYPE))
							{
								obj1 = Byte.valueOf(Byte.parseByte(s1));
							} else
								if(class2.isAssignableFrom(Boolean.TYPE))
								{
									obj1 = Boolean.valueOf(Boolean.parseBoolean(s1));
								} else
									if(class2.isAssignableFrom(Float.TYPE))
									{
										obj1 = Float.valueOf(Float.parseFloat(s1));
									} else
										if(class2.isAssignableFrom(Double.TYPE))
										{
											obj1 = Double.valueOf(Double.parseDouble(s1));
										}
				if(obj1 == null)
				{
					continue;
				}
				if(obj1 instanceof Number)
				{
					double d = ((Number)obj1).doubleValue();
					if(mlprop.min() != (-1.0D / 0.0D) && d < mlprop.min() || mlprop.max() != (1.0D / 0.0D) && d > mlprop.max())
					{
						continue;
					}
				}
				logger.finer((new StringBuilder(String.valueOf(s))).append(" set to ").append(obj1).toString());
				if(!obj1.equals(obj))
				{
					field.set(null, obj1);
				}
			} else
			{
				logger.finer((new StringBuilder(String.valueOf(s))).append(" not in config, using default: ").append(obj).toString());
				properties.setProperty(s, obj.toString());
			}
		}

		if(!properties.isEmpty() && (file.exists() || file.createNewFile()) && file.canWrite())
		{
			properties.store(new FileOutputStream(file), stringbuilder.toString());
		}
	}

	public static void TakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack)
	{
		BaseMod basemod;
		for(Iterator iterator = modList.iterator(); iterator.hasNext(); basemod.TakenFromCrafting(entityplayer, itemstack))
		{
			basemod = (BaseMod)iterator.next();
		}

	}

	public static void TakenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
	{
		BaseMod basemod;
		for(Iterator iterator = modList.iterator(); iterator.hasNext(); basemod.TakenFromFurnace(entityplayer, itemstack))
		{
			basemod = (BaseMod)iterator.next();
		}

	}

	public static void ThrowException(String s, Throwable throwable)
	{
		Minecraft minecraft = getMinecraftInstance();
		if(minecraft != null)
		{
			minecraft.displayUnexpectedThrowable(new UnexpectedThrowable(s, throwable));
		} else
		{
			throw new RuntimeException(throwable);
		}
	}

	private static void ThrowException(Throwable throwable)
	{
		ThrowException("Exception occured in ModLoader", throwable);
	}

	private ModLoader()
	{
	}

	private static final List animList = new LinkedList();
	private static final Map blockModels = new HashMap();
	private static final Map blockSpecialInv = new HashMap();
	private static final File cfgdir;
	private static final File cfgfile;
	public static Level cfgLoggingLevel;
	private static Map classMap = null;
	private static long clock = 0L;
	public static final boolean DEBUG = false;
	private static Field field_animList = null;
	private static Field field_armorList = null;
	private static Field field_blockList = null;
	private static Field field_modifiers = null;
	private static Field field_TileEntityRenderers = null;
	private static boolean hasInit = false;
	private static int highestEntityId = 3000;
	private static final Map inGameHooks = new HashMap();
	private static final Map inGUIHooks = new HashMap();
	private static Minecraft instance = null;
	private static int itemSpriteIndex = 0;
	private static int itemSpritesLeft = 0;
	private static final Map keyList = new HashMap();
	private static final File logfile = new File(Minecraft.getMinecraftDir(), "ModLoader.txt");
	private static final Logger logger = Logger.getLogger("ModLoader");
	private static FileHandler logHandler = null;
	private static Method method_RegisterEntityID = null;
	private static Method method_RegisterTileEntity = null;
	private static final File modDir = new File(Minecraft.getMinecraftDir(), "/mods/");
	private static final LinkedList modList = new LinkedList();
	private static int nextBlockModelID = 1000;
	private static final Map overrides = new HashMap();
	public static final Properties props = new Properties();
	private static BiomeGenBase standardBiomes[];
	private static int terrainSpriteIndex = 0;
	private static int terrainSpritesLeft = 0;
	private static String texPack = null;
	private static boolean texturesAdded = false;
	private static final boolean usedItemSprites[] = new boolean[256];
	private static final boolean usedTerrainSprites[] = new boolean[256];
	public static final String VERSION = "ModLoader Beta 1.7.3";

	static 
	{
		cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
		cfgfile = new File(cfgdir, "ModLoader.cfg");
		cfgLoggingLevel = Level.FINER;
	}
}
